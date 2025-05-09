package com.obast.charer.system.service.system;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.constant.Constants;
import com.obast.charer.common.constant.GlobalConstants;
import com.obast.charer.common.enums.LogicTypeEnum;
import com.obast.charer.common.enums.LoginType;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.exception.user.UserException;
import com.obast.charer.common.log.event.LogininforEvent;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.model.RoleDTO;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.*;
import com.obast.charer.common.web.utils.ServletUtils;
import com.obast.charer.data.system.ISysUserData;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.system.config.properties.CaptchaProperties;
import com.obast.charer.system.dto.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

/**
 * 登录校验方法
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginService {

    private final ISysUserData userData;
    private final CaptchaProperties captchaProperties;
    private final ISysPermissionService permissionService;


    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaEnabled = captchaProperties.getEnable();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, code, uuid);
        }

        SysUserVo user = loadUserByUsername(username);

        log.debug("登陆信息: {}", user);
        checkLogin(username, () -> !BCrypt.checkpw(password, user.getPassword()));

        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByPlatform(loginUser, PlatformTypeEnum.Web);

        log.debug("web用户: {}", loginUser);

        recordLoginInfo(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getId());
        return StpUtil.getTokenValue();
    }




    /**
     * 退出登录
     */
    public void logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
            if(loginUser==null){
                return;
            }

            StpUtil.logout();
            recordLoginInfo(loginUser.getUserName(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        }
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    private void recordLoginInfo(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();

        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            recordLoginInfo(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new BizException("验证码过期");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLoginInfo(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new BizException("验证码错误");
        }
    }

    private SysUserVo loadUserByUsername(String username) {
        SysUser sysUser = userData.findByUserName(username);
        return MapstructUtils.convert(sysUser, SysUserVo.class);
    }


    /**
     * 构建登录用户
     */
    private LoginUser buildLoginUser(SysUserVo user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setLogicType(LogicTypeEnum.System);
        loginUser.setUserId(user.getId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUserName(user.getUserName());
        loginUser.setUserType(user.getUserType());

        loginUser.setMenuPermission(permissionService.getMenuPermission(user.getId()));
        loginUser.setRolePermission(permissionService.getRolePermission(user.getId()));
        loginUser.setDeptName(ObjectUtil.isNull(user.getDept()) ? "" : user.getDept().getDeptName());
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(String userId) {
        SysUser sysUser = userData.findById(userId);
        sysUser.setLoginIp(ServletUtils.getClientIP());
        sysUser.setLoginDate(DateUtils.getNowDate());
        sysUser.setUpdateBy(userId);
        userData.save(sysUser);
    }

    /**
     * 登录校验
     */
    private void checkLogin(String username, Supplier<Boolean> supplier) {
        String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = RedisUtils.getCacheObject(errorKey);
        // 锁定时间内登录 则踢出
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
            recordLoginInfo(username, loginFail, MessageUtils.message(LoginType.PASSWORD.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException("重试达到最大限制");
        }

        if (supplier.get()) {
            // 是否第一次
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // 达到规定错误次数 则锁定登录
            if (errorNumber.equals(maxRetryCount)) {
                RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
                recordLoginInfo(username, loginFail, MessageUtils.message(LoginType.PASSWORD.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException("重试达到最大限制");

            } else {
                // 未达到规定错误次数 则递增
                RedisUtils.setCacheObject(errorKey, errorNumber);
                recordLoginInfo(username, loginFail, MessageUtils.message(LoginType.PASSWORD.getRetryLimitCount(), errorNumber));
                throw new UserException(String.format("错误次数:%s", errorNumber));

            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }
}
