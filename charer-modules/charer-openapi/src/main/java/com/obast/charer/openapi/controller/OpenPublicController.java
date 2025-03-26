package com.obast.charer.openapi.controller;

import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.openapi.service.IOpenAdsService;
import com.obast.charer.openapi.service.IOpenSysConfigService;
import com.obast.charer.openapi.util.ResourceUtil;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：公共页面
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/openapi/v1/public")
@Api(tags = "公共页面")
public class OpenPublicController extends BaseController {

  @Autowired
  private IOpenSysConfigService openSysConfigService;

    @Autowired
    private IOpenAdsService openAdsService;

  @SaIgnore
  @ApiOperation("隐私协议")
  @GetMapping(value = "/privacy", produces = "text/html;charset=UTF-8")
  public String privacy() {
      String template = ResourceUtil.getResourceText("/html/public.html");
      String str = (String) openSysConfigService.selectByConfigKey("app.user.privacy");
      str = template.replace("{body}", str);
      log.debug("隐私协议: {}", str);
      return str;
  }

  @SaIgnore
  @ApiOperation("用户协议")
  @GetMapping(value = "/agreement", produces = "text/html;charset=UTF-8")
  public String agreement() {
      String template = ResourceUtil.getResourceText("/html/public.html");
      String str = (String) openSysConfigService.selectByConfigKey("app.user.agreement");
      str = template.replace("{body}", str);
      log.debug("用户协议: {}", str);
      return str;
  }

    @SaIgnore
    @ApiOperation("联系客服")
    @GetMapping(value = "/service", produces = "text/html;charset=UTF-8")
    public String service() {
        String template = ResourceUtil.getResourceText("/html/public.html");
        String str = (String) openSysConfigService.selectByConfigKey("app.user.service");
        str = template.replace("{body}", str);
        log.debug("联系客服: {}", str);
        return str;
    }

    @SaIgnore
    @ApiOperation("删除账号")
    @GetMapping(value = "/cannelAccount", produces = "text/html;charset=UTF-8")
    public String cannelAccount() {
        return "";
    }

    @SaIgnore
    @ApiOperation("广告内容")
    @GetMapping(value = "/ads", produces = "text/html;charset=UTF-8")
    public String ads(@RequestParam String id) {
        String template = ResourceUtil.getResourceText("/html/public.html");
        String str = openAdsService.queryDetail(id).getContent();
        return template.replace("{body}", str);
    }
}
