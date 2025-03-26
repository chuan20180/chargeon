package com.obast.charer.system.service.system;

import com.obast.charer.common.constant.Constants;
import com.obast.charer.common.constant.GlobalConstants;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.LogicTypeEnum;
import com.obast.charer.common.enums.LoginType;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.exception.user.UserException;
import com.obast.charer.common.utils.DateUtils;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.MessageUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.log.event.LogininforEvent;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.model.RoleDTO;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.tenant.helper.TenantHelper;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.system.config.properties.CaptchaProperties;
import com.obast.charer.common.web.utils.ServletUtils;
import com.obast.charer.data.system.ISysUserData;
import com.obast.charer.system.dto.vo.SysUserVo;
import com.obast.charer.system.dto.vo.tenant.SysTenantVo;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysUser;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.system.service.platform.ISysTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
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
    private final ISysTenantService tenantService;


    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    //private String authUrl="https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String tenantId, String username, String password, String code, String uuid) {
        boolean captchaEnabled = captchaProperties.getEnable();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(tenantId, username, code, uuid);
        }
        // 校验租户
        checkTenant(tenantId);

        //未登录前临时设置租户id
        LoginHelper.setTenantId(tenantId);
        SysUserVo user = loadUserByUsername(tenantId, username);


        log.debug("登陆信息: {}", user);
        user.setTenantId(tenantId);
        checkLogin(LoginType.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByPlatform(loginUser, PlatformTypeEnum.Web);

        log.debug("web用户: {}", loginUser);

        recordLoginInfo(loginUser.getTenantId(), username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
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
            if (LoginHelper.isSuperAdmin()) {
                // 超级管理员 登出清除动态租户
                TenantHelper.clearDynamic();
            }
            StpUtil.logout();
            recordLoginInfo(loginUser.getTenantId(), loginUser.getUserName(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        }
    }

    /**
     * 记录登录信息
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    private void recordLoginInfo(String tenantId, String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

    /**
     * 校验短信验证码
     */
    private boolean validateSmsCode(String tenantId, String phonenumber, String smsCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + phonenumber);
        if (StringUtils.isBlank(code)) {
            recordLoginInfo(tenantId, phonenumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new BizException(MessageUtils.message("user.jcaptcha.expire"));
        }
        return code.equals(smsCode);
    }

    /**
     * 校验邮箱验证码
     */
    private boolean validateEmailCode(String tenantId, String email, String emailCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + email);
        if (StringUtils.isBlank(code)) {
            recordLoginInfo(tenantId, email, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new BizException("验证码过期");
        }
        return code.equals(emailCode);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    public void validateCaptcha(String tenantId, String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            recordLoginInfo(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new BizException("验证码过期");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLoginInfo(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new BizException("验证码错误");
        }
    }

    private SysUserVo loadUserByUsername(String tenantId, String username) {
        SysUser user = userData.findTenantUserByUserName(username,tenantId);

        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("登录用户不存在");
        } else if (user.getStatus().equals(EnableStatusEnum.Disabled)) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("用户被停用");
        }
        if (TenantHelper.isEnable()) {
            return user.to(SysUserVo.class);
        }
        SysUser sysUser = userData.findByUserName(username);
        return MapstructUtils.convert(sysUser, SysUserVo.class);
    }


    /**
     * 构建登录用户
     */
    private LoginUser buildLoginUser(SysUserVo user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setLogicType(LogicTypeEnum.System);
        loginUser.setTenantId(user.getTenantId());
        loginUser.setAgentId(user.getAgentId());
        loginUser.setDealerId(user.getDealerId());
        loginUser.setIsTenantAdmin(user.getIsTenantAdmin());
        loginUser.setIsAgentAdmin(user.getIsAgentAdmin());
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
    private void checkLogin(LoginType loginType, String tenantId, String username, Supplier<Boolean> supplier) {
        String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数(可自定义限制策略 例如: key + username + ip)
        Integer errorNumber = RedisUtils.getCacheObject(errorKey);
        // 锁定时间内登录 则踢出
        if (ObjectUtil.isNotNull(errorNumber) && errorNumber.equals(maxRetryCount)) {
            recordLoginInfo(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException("重试达到最大限制");
        }

        if (supplier.get()) {
            // 是否第一次
            errorNumber = ObjectUtil.isNull(errorNumber) ? 1 : errorNumber + 1;
            // 达到规定错误次数 则锁定登录
            if (errorNumber.equals(maxRetryCount)) {
                RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
                recordLoginInfo(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException("重试达到最大限制");

            } else {
                // 未达到规定错误次数 则递增
                RedisUtils.setCacheObject(errorKey, errorNumber);
                recordLoginInfo(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
                throw new UserException(String.format("错误次数:%s", errorNumber));

            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }

    private void checkTenant(String tenantId) {
        if (!TenantHelper.isEnable()) {
            return;
        }
        if (TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
            return;
        }
        SysTenantVo tenant = tenantService.queryByTenantId(tenantId);
        if (ObjectUtil.isNull(tenant)) {
            log.info("登录租户：{} 不存在.", tenantId);
            throw new BizException(ErrCode.TENANT_NOT_FOUND);
        } else if (EnableStatusEnum.Disabled.equals(tenant.getStatus())) {
            log.info("登录租户：{} 已被停用.", tenantId);
            throw new BizException(ErrCode.TENANT_DISABLE);
        } else if (ObjectUtil.isNotNull(tenant.getExpireTime())
                && new Date().after(tenant.getExpireTime())) {
            log.info("登录租户：{} 已超过有效期.", tenantId);
            throw new BizException(ErrCode.TENANT_EXPIRE);
        }
    }

}
