package com.obast.charer.openapi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.obast.charer.api.system.feign.IRemoteSmsService;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.enums.*;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.web.utils.ServletUtils;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.ICustomerLoginData;
import com.obast.charer.data.business.ISmsRecordData;
import com.obast.charer.data.system.ISysAppData;
import com.obast.charer.data.system.ISysUserData;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.sms.SmsRecord;
import com.obast.charer.model.system.SysApp;
import com.obast.charer.model.system.SysUser;
import com.obast.charer.openapi.config.request.RequestLocale;
import com.obast.charer.openapi.config.request.RequestLocaleHolder;
import com.obast.charer.openapi.dto.bo.*;
import com.obast.charer.openapi.dto.vo.AccountLoginVo;
import com.obast.charer.openapi.dto.vo.LoginCodeVo;
import com.obast.charer.openapi.dto.vo.LoginMobileVo;
import com.obast.charer.openapi.dto.vo.OpenLoginVo;
import com.obast.charer.openapi.service.OpenBaseService;
import com.obast.charer.openapi.util.WechatUtil;
import com.obast.charer.qo.CustomerQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class OpenBaseServiceImpl implements OpenBaseService {

    @Autowired
    private ISysUserData sysUserData;

    @Autowired
    private ISysAppData sysAppData;

    @Autowired
    private ISmsRecordData smsRecordData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private ICustomerLoginData customerLoginData;

    @Autowired
    private WechatUtil wechatUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IRemoteSmsService remoteSmsService;

    @Override
    public String getToken(TokenVerifyBo bo) {
        String appId = bo.getAppId();

        SysApp sysApp = sysAppData.findByAppId(appId);
        if (sysApp == null){
            throw new BizException(ErrCode.API_LOGIN_ERROR);
        }

        SysUser sysUser = sysUserData.findByUserName("admin");
        if (sysUser == null){
            throw new BizException(ErrCode.API_LOGIN_ERROR);
        }

        return StpUtil.getTokenValue();
    }

    @Override
    public OpenLoginVo refreshToken(TokenRefreshBo bo) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        log.debug("刷新token, 登陆信息: {}, 请求: {}", loginUser, RequestLocaleHolder.getLocale());
        if(loginUser == null) {
            log.error("同步用户信息失败 {} ({})", ErrCode.API_LOGIN_ERROR.getKey(), ErrCode.API_LOGIN_ERROR.getValue());
            return null;
        }

        Customer customer = customerData.findByUserName(loginUser.getUserName());
        if (customer == null){
            log.error("同步用户信息失败 {} ({})", ErrCode.CUSTOMER_NOT_FOUND.getKey(), ErrCode.CUSTOMER_NOT_FOUND.getValue());
            return null;
        }

        if (customer.getStatus().equals(EnableStatusEnum.Disabled)){
            log.error("同步用户信息失败 {} ({})", ErrCode.CUSTOMER_BE_DISABLED.getKey(), ErrCode.CUSTOMER_BE_DISABLED.getValue());
            return null;
        }

        if (loginUser.getCustomerLoginId() == null){
            log.error("同步用户信息失败 {} ({})", ErrCode.CUSTOMER_LOGIN_NOT_FOUND.getKey(), ErrCode.CUSTOMER_LOGIN_NOT_FOUND.getValue());
            return null;
        }

        customer.setLoginDate(new Date());
        customer.setLoginIp(ServletUtils.getClientIP());
        customerData.save(customer);

        return new OpenLoginVo( StpUtil.getTokenValue(), customer);
    }

    @Override
    public void appUploadPushClientId(AppSyncUserBo bo) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        log.debug("上传app客户端cid, 登陆信息: {}, 请求: {}", loginUser, bo);
        if(loginUser == null) {
            log.error("同步用户信息失败 {} ({})", ErrCode.API_LOGIN_ERROR.getKey(), ErrCode.API_LOGIN_ERROR.getValue());
            return;
        }

        Customer customer = customerData.findByUserName(loginUser.getUserName());
        if (customer == null){
            log.error("同步用户信息失败 {} ({})", ErrCode.CUSTOMER_NOT_FOUND.getKey(), ErrCode.CUSTOMER_NOT_FOUND.getValue());
            return;
        }

        RequestLocale requestLocale = RequestLocaleHolder.getLocale();

        if(requestLocale.getPlatform() == null) {
            log.error("同步用户信息失败 {} ({})", ErrCode.APP_SYNC_USER_PLATFORM_NULL.getKey(), ErrCode.APP_SYNC_USER_PLATFORM_NULL.getValue());
            return;
        }

        CustomerLogin customerLogin = customerLoginData.findByCustomerIdAndPlatform(customer.getId(), requestLocale.getPlatform());
        if(customerLogin == null) {
            log.error("同步用户信息失败 {} ({})", ErrCode.APP_SYNC_USER_CUSTOMER_LOGIN_NULL.getKey(), ErrCode.APP_SYNC_USER_CUSTOMER_LOGIN_NULL.getValue());
            return;
        }

        if(StringUtils.isBlank(bo.getCid())) {
            log.error("同步用户信息失败 {} ({})", ErrCode.APP_SYNC_USER_CID_NULL.getKey(), ErrCode.APP_SYNC_USER_CID_NULL.getValue());
            return;
        }

        customerLogin.setDn(bo.getCid());
        customerLoginData.save(customerLogin);
    }

    /**
     * 手机号登陆
     */
    @Override
    public LoginMobileVo mobileLogin(MobileLoginBo bo) {

        String mobile = bo.getMobile();


        Response<?> sendResult = remoteSmsService.sendVerificationCode(SmsTypeEnum.Login.name(), mobile);
        log.debug("验证码发送结果: {}", JsonUtils.toJsonString(sendResult));
        if(sendResult.getCode() != 200 ) {
            throw new BizException(ErrCode.SMS_SEND_FAIL, sendResult.getMessage());
        }

        SmsRecord smsRecord;
        try {
             smsRecord = JsonUtils.parse(JsonUtils.toJsonString(sendResult.getData()), SmsRecord.class);
        } catch (Exception e) {
            throw new BizException(ErrCode.SMS_SEND_EXCEPTION);
        }

        if(smsRecord == null) {
            throw new BizException(ErrCode.APP_SMS_RECORD_NOT_FOUND);
        }
        return new LoginMobileVo(smsRecord.getId(), mobile, null);
    }

    @Override
    public LoginCodeVo mobileCode(MobileCodeBo bo) {

        Response<?> sendResult = remoteSmsService.verifyVerificationCode(SmsTypeEnum.Login.name(), bo.getMobile(), bo.getCode());
        if(sendResult.getCode() != 200 ) {
            throw new BizException(ErrCode.APP_SMS_VERIFY_CODE_ERROR, sendResult.getMessage());
        }

        Customer customer = customerData.findByUserName(bo.getMobile());
        if(customer == null) {
            return new LoginCodeVo(true, "", customer);
        } else {

            RequestLocale requestLocale = RequestLocaleHolder.getLocale();

            CustomerLogin customerLogin = customerLoginData.findByCustomerIdAndPlatform(customer.getId(), requestLocale.getPlatform());

            if(customerLogin == null) {
                customerLogin = new CustomerLogin();
                customerLogin.setCustomerId(customer.getId());
                customerLogin.setLoginType(LoginTypeEnum.Code);
                customerLogin.setPlatform(requestLocale.getPlatform());
                customerLogin.setOs(requestLocale.getOs());
                customerLogin.setDevice(requestLocale.getDevice());
                customerLogin.setVersion(requestLocale.getVersion());
                customerLogin.setLanguage(requestLocale.getLanguage());

                customerLogin.setLoginIp(request.getRemoteAddr());
                customerLogin.setLoginDate(new Date());
                customerLoginData.save(customerLogin);
            }

            LoginUser loginUser = new LoginUser();
            loginUser.setLogicType(LogicTypeEnum.Openapi);

            loginUser.setCustomerLoginId(customerLogin.getId());
            loginUser.setUserId(customer.getId());
            loginUser.setUserName(customer.getUserName());

            loginUser.setLanguage(requestLocale.getLanguage());
            loginUser.setVersion(requestLocale.getVersion());
            loginUser.setPlatform(requestLocale.getPlatform());
            loginUser.setDevice(requestLocale.getDevice());

            //记录登陆信息
            recordCustomerLogin(loginUser);

            // 生成token
            LoginHelper.loginByPlatform(loginUser, PlatformTypeEnum.App);
            String accessToken = StpUtil.getTokenValue();
            return new LoginCodeVo(true, accessToken, customer);
        }
    }

    @Override
    public OpenLoginVo mobileInfo(MobileInfoBo bo) {
        CustomerQueryBo queryBo = new CustomerQueryBo();
        queryBo.setUserName(bo.getMobile());
        boolean exists = customerData.checkUserNameUnique(queryBo);
        if(exists) {
            throw new BizException(ErrCode.CUSTOMER_USER_NAME_EXIST);
        }

        SmsRecord smsRecord = smsRecordData.findById(bo.getMessageId());
        if(smsRecord == null) {
            throw new BizException(ErrCode.APP_SMS_RECORD_NOT_FOUND);
        }

        String nickName = bo.getNickName();
        if(StringUtils.isBlank(nickName)) {
            throw new BizException(ErrCode.CUSTOMER_USER_NICKNAME_EMPTY);
        }

        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();

        Customer customer = new Customer();
        customer.setUserName(bo.getMobile());
        customer.setMobile(bo.getMobile());
        customer.setNickName(nickName);
        customer.setType(CustomerTypeEnum.App);
        customer.setStatus(EnableStatusEnum.Enabled);
        customer.setLoginDate(new Date());
        customer.setLoginIp(request.getRemoteAddr());

        Customer savedCustomer = customerData.add(customer);

        LoginUser loginUser = new LoginUser();
        loginUser.setLogicType(LogicTypeEnum.Openapi);

        loginUser.setUserId(savedCustomer.getId());
        loginUser.setUserName(savedCustomer.getUserName());

        loginUser.setCustomerDn(bo.getDn());

        RequestLocale requestLocale = RequestLocaleHolder.getLocale();
        loginUser.setLanguage(requestLocale.getLanguage());
        loginUser.setVersion(requestLocale.getVersion());
        loginUser.setPlatform(requestLocale.getPlatform());
        loginUser.setDevice(requestLocale.getDevice());

        //记录登陆信息
        recordCustomerLogin(loginUser);

        // 生成token
        LoginHelper.loginByPlatform(loginUser, PlatformTypeEnum.App);
        String accessToken = StpUtil.getTokenValue();

        return new OpenLoginVo(accessToken, savedCustomer);
    }

    @Override
    public AccountLoginVo accountLogin(AccountLoginBo bo) {
        log.error("[用户登陆] 登陆信息, data= {}", bo);
        Customer customer = customerData.findByUserName(bo.getUsername());
        if(customer == null) {
            log.error("[用户登陆] 帐号登陆失败, msg= {}", ErrCode.APP_LOGIN_USERNAME_NOT_FOUND.getValue());
            throw new BizException(ErrCode.APP_LOGIN_USERNAME_NOT_FOUND);
        }

        if(StringUtils.isBlank(customer.getPassword()) || !customer.getPassword().equals(bo.getPassword())) {
            log.error("[用户登陆] 帐号登陆失败, msg= {}", ErrCode.APP_LOGIN_PASSWORD_INVALID.getValue());
            throw new BizException(ErrCode.APP_LOGIN_PASSWORD_INVALID);
        }

        RequestLocale requestLocale = RequestLocaleHolder.getLocale();

        CustomerLogin customerLogin = customerLoginData.findByCustomerIdAndPlatform(customer.getId(), requestLocale.getPlatform());

        if(customerLogin == null) {
            customerLogin = new CustomerLogin();
            customerLogin.setCustomerId(customer.getId());
            customerLogin.setLoginType(LoginTypeEnum.Account);
            customerLogin.setPlatform(requestLocale.getPlatform());
            customerLogin.setOs(requestLocale.getOs());
            customerLogin.setDevice(requestLocale.getDevice());
            customerLogin.setVersion(requestLocale.getVersion());
            customerLogin.setLanguage(requestLocale.getLanguage());

            customerLogin.setLoginIp(request.getRemoteAddr());
            customerLogin.setLoginDate(new Date());
            customerLoginData.save(customerLogin);
        }


        LoginUser loginUser = new LoginUser();
        loginUser.setLogicType(LogicTypeEnum.Openapi);

        loginUser.setUserId(customer.getId());
        loginUser.setCustomerLoginId(customerLogin.getId());
        loginUser.setCustomerDn(customerLogin.getDn());
        loginUser.setUserName(customer.getUserName());

        loginUser.setLanguage(requestLocale.getLanguage());
        loginUser.setVersion(requestLocale.getVersion());
        loginUser.setPlatform(requestLocale.getPlatform());
        loginUser.setDevice(requestLocale.getDevice());

        //记录登陆信息
        recordCustomerLogin(loginUser);

        // 生成token
        LoginHelper.loginByPlatform(loginUser, PlatformTypeEnum.Weixin);
        String accessToken = StpUtil.getTokenValue();
        return new AccountLoginVo(accessToken, customer);
    }

    private void recordCustomerLogin(LoginUser loginUser) {

        RequestLocale requestLocale = RequestLocaleHolder.getLocale();
        String ip = ServletUtils.getClientIP();

        CustomerLogin customerLogin = customerLoginData.findByCustomerIdAndPlatform(loginUser.getUserId(), requestLocale.getPlatform());
        if(customerLogin == null) {
            customerLogin = new CustomerLogin();
            customerLogin.setCustomerId(loginUser.getUserId());
        }

        customerLogin.setDn(loginUser.getCustomerDn());
        customerLogin.setOs(requestLocale.getOs());
        customerLogin.setPlatform(requestLocale.getPlatform());
        customerLogin.setVersion(requestLocale.getVersion());
        customerLogin.setDevice(requestLocale.getDevice());
        customerLogin.setLanguage(requestLocale.getLanguage());

        customerLogin.setLoginIp(ip);
        customerLogin.setLoginDate(new Date());

        customerLoginData.save(customerLogin);
    }

    @Override
    public OpenLoginVo wxLogin(WxLoginBo bo) {

        log.debug("[微信登陆]登陆请求信息 data={}", bo);
        if(bo == null) {
            throw new BizException(ErrCode.WEIXIN_PARAM_EMPTY);
        }
        String encryptedData = bo.getEncryptedData();
        if(encryptedData == null) {
            throw new BizException(ErrCode.WEIXIN_PARAM_ENCRYPTED_EMPTY);
        }
        String iv = bo.getIv();
        if(iv == null) {
            throw new BizException(ErrCode.WEIXIN_PARAM_IV_EMPTY);
        }
        String sessionKey = bo.getSessionKey();
        if(sessionKey == null) {
            throw new BizException(ErrCode.WEIXIN_PARAM_SESSION_KEY_EMPTY);
        }
        String openId = bo.getOpenId();
        if(openId == null) {
            throw new BizException(ErrCode.WEIXIN_PARAM_OPEN_ID_EMPTY);
        }

        WechatUtil.WxLoginResult result = wechatUtil.decryptData(sessionKey, encryptedData, iv);
        log.debug("解密手机号结果: {}", result);

        String mobile = result.getPurePhoneNumber();
        if(mobile == null) {
            throw new BizException(ErrCode.WEIXIN_PARSE_MOBILE_EXCEPTION);
        }

        Customer customer = customerData.findByUserName(mobile);
        if(customer == null) {
            //检查是否有没有手机号的用户
            CustomerLogin customerLogin = customerLoginData.findByDnAndPlatform(openId, PlatformTypeEnum.Weixin);
            if(customerLogin != null) {
                Customer loginCustomer = customerData.findById(customerLogin.getCustomerId());
                if(StringUtils.isBlank(loginCustomer.getUserName())) {
                    loginCustomer.setUserName(mobile);
                    loginCustomer.setNickName(mobile);
                    loginCustomer.setMobile(mobile);
                    customer = customerData.update(loginCustomer);
                }
            }
        }

        RequestLocale requestLocale = RequestLocaleHolder.getLocale();

        if(customer == null) {
            customer = new Customer();
            customer.setUserName(mobile);
            customer.setMobile(mobile);
            customer.setNickName(mobile);
            customer.setBalanceAmount(new BigDecimal(0));
            customer.setGiveAmount(new BigDecimal(0));
            customer.setType(CustomerTypeEnum.Wechat);
            customer.setStatus(EnableStatusEnum.Enabled);
            customer.setLoginDate(new Date());
            customer.setLoginIp(request.getRemoteAddr());
            customer = customerData.add(customer);
        }

        CustomerLogin customerLogin = customerLoginData.findByCustomerIdAndPlatform(customer.getId(), PlatformTypeEnum.Weixin);

        if(customerLogin == null) {
            customerLogin = new CustomerLogin();
            customerLogin.setLoginType(LoginTypeEnum.Wechat);
            customerLogin.setCustomerId(customer.getId());
            customerLogin.setDn(openId);
            customerLogin.setOs(requestLocale.getOs());
            customerLogin.setPlatform( PlatformTypeEnum.Weixin);
            customerLogin.setDevice(requestLocale.getDevice());
            customerLogin.setVersion(requestLocale.getVersion());
            customerLogin.setLanguage(requestLocale.getLanguage());
            customerLogin = customerLoginData.add(customerLogin);
        }

        customerLogin.setLoginIp(request.getRemoteAddr());
        customerLogin.setLoginDate(new Date());
        customerLoginData.save(customerLogin);


        LoginUser loginUser = new LoginUser();
        loginUser.setLogicType(LogicTypeEnum.Openapi);

        loginUser.setUserId(customer.getId());
        loginUser.setUserName(customer.getUserName());
        loginUser.setPlatform(PlatformTypeEnum.Weixin);
        loginUser.setCustomerLoginId(customerLogin.getId());
        loginUser.setCustomerDn(openId);

        loginUser.setLanguage(requestLocale.getLanguage());
        loginUser.setVersion(requestLocale.getVersion());
        loginUser.setPlatform(requestLocale.getPlatform());
        loginUser.setDevice(requestLocale.getDevice());

        //记录登陆信息
        recordCustomerLogin(loginUser);

        // 生成token
        LoginHelper.loginByPlatform(loginUser, PlatformTypeEnum.Weixin);
        String accessToken = StpUtil.getTokenValue();

        return new OpenLoginVo(accessToken, customer);
    }

    public WechatUtil.WxAuthorizeResult wxAuthorize(WxAuthorizeBo bo) {
        if(bo == null) {
            throw new BizException(ErrCode.WEIXIN_PARAM_EMPTY);
        }

        String code = bo.getCode();
        if(code == null) {
            throw new BizException(ErrCode.WEIXIN_PARAM_CODE_EMPTY);
        }

        return wechatUtil.authorize(code);

    }
}
