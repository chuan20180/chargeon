package com.obast.charer.openapi.config.auth;

import com.obast.charer.CharerOpenapiApplication;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.system.ISysAppData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysApp;
import com.obast.charer.openapi.config.request.RequestLocale;
import com.obast.charer.openapi.config.request.RequestLocaleHolder;
import org.springframework.stereotype.Component;

/**
 * OpenAPI 接口认证工具类
 */
@Component
public class AuthUtil {

	/** 
 	 * 检验当前会话是否已经登录，如未登录，则抛出异常 
 	 */
 	public static void checkAuth() {

		RequestLocale requestLocale = RequestLocaleHolder.getLocale();
		ISysAppData sysAppData = CharerOpenapiApplication.context.getBean(ISysAppData.class);

		String appId = requestLocale.getApiId();
		if(StringUtils.isBlank(appId)) {
			throw new BizException(ErrCode.PARAMS_EXCEPTION);
		}

		SysApp sysApp = sysAppData.findByAppId(requestLocale.getApiId());

		if(sysApp == null) {
			throw new BizException(ErrCode.SYS_APP_NOT_EXSIT);
		}

		if(sysApp.getStatus().equals(EnableStatusEnum.Disabled)) {
			throw new BizException(ErrCode.SYS_APP_DISABLED);
		}

 	}

}
