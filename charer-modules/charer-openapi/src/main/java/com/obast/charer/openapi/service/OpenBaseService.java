package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.bo.*;
import com.obast.charer.openapi.dto.vo.AccountLoginVo;
import com.obast.charer.openapi.dto.vo.LoginCodeVo;
import com.obast.charer.openapi.dto.vo.LoginMobileVo;
import com.obast.charer.openapi.dto.vo.OpenLoginVo;
import com.obast.charer.openapi.util.WechatUtil;

public interface OpenBaseService {

    String getToken(TokenVerifyBo bo);

    OpenLoginVo refreshToken(TokenRefreshBo bo);

    void appUploadPushClientId(AppSyncUserBo bo);

    LoginMobileVo mobileLogin(MobileLoginBo bo);

    LoginCodeVo mobileCode(MobileCodeBo bo);

    OpenLoginVo mobileInfo(MobileInfoBo bo);

    AccountLoginVo accountLogin(AccountLoginBo bo);

    OpenLoginVo wxLogin(WxLoginBo bo);

    WechatUtil.WxAuthorizeResult wxAuthorize(WxAuthorizeBo bo);

}
