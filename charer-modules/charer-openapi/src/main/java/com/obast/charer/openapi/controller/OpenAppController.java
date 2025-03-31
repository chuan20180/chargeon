package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.properties.OpenapiProperties;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.data.business.INotifyConfigData;
import com.obast.charer.data.system.ISysAppData;
import com.obast.charer.model.map.MapConfig;
import com.obast.charer.model.system.SysApp;
import com.obast.charer.openapi.config.request.RequestLocale;
import com.obast.charer.openapi.config.request.RequestLocaleHolder;
import com.obast.charer.openapi.dto.bo.AppSyncUserBo;
import com.obast.charer.openapi.dto.bo.TokenRefreshBo;
import com.obast.charer.openapi.dto.vo.OpenAppSettingVo;
import com.obast.charer.openapi.dto.vo.OpenLoginVo;
import com.obast.charer.openapi.dto.vo.OpenNotifyConfigVo;
import com.obast.charer.openapi.service.OpenBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：配置项管理
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/openapi/v1/app")
@Api(tags = "配置项管理")
public class OpenAppController extends BaseController {

  @Autowired
  private ISysAppData sysAppData;

  @Autowired
  private INotifyConfigData notifyConfigData;

  @Autowired
  private OpenBaseService openBaseService;

  @Autowired
  private OpenapiProperties openapiProperties;

  @SaIgnore
  @ApiOperation("参数列表")
  @PostMapping("/setting")
  public OpenAppSettingVo setting() {
    RequestLocale requestLocale = RequestLocaleHolder.getLocale();
    SysApp sysApp = sysAppData.findByAppId(requestLocale.getApiId());
//    MapConfig map = mapConfigData.findMapConfigByTenantId(sysApp.getTenantId());
//    Map<String, Object> config = openSysConfigService.querySysConfigByTenantId(sysApp.getTenantId());

    //todo
    MapConfig map = null;
    Map<String, Object> config = null;

    OpenNotifyConfigVo notify = new OpenNotifyConfigVo(notifyConfigData.findWechatTemplateIds());

    return new OpenAppSettingVo(sysApp, map, config, openapiProperties, notify);
  }

  @ApiOperation("刷新token")
  @PostMapping("/refreshToken")
  public OpenLoginVo refreshToken(@RequestBody @Validated Request<TokenRefreshBo> request) {
    return openBaseService.refreshToken(request.getData());
  }

  @ApiOperation("上传app Push Client Id")
  @PostMapping("/uploadPushClientId")
  public void uploadPushClientId(@RequestBody @Validated Request<AppSyncUserBo> request) {
      openBaseService.appUploadPushClientId(request.getData());
  }
}
