package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysConfigBo;
import com.obast.charer.system.dto.vo.SysConfigVo;
import com.obast.charer.system.service.system.ISysConfigService;
import com.obast.charer.qo.SysConfigQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置 信息操作处理
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/config")
@Api(tags = "参数配置 信息操作处理")
public class SysConfigController extends BaseController {

  private final ISysConfigService configService;

  @ApiOperation("获取参数配置列表")
  @SaCheckPermission("system:config:list")
  @PostMapping("/list")
  public Paging<SysConfigVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<SysConfigQueryBo> query) {
    return configService.queryPage(query);
  }

  @ApiOperation("根据参数编号获取详细信息")
  @PostMapping(value = "/getDetail")
  public SysConfigVo getInfo(@RequestBody @Validated Request<String> request) {
    return configService.selectConfigById(request.getData());
  }


  @ApiOperation("根据参数键名查询参数值")
  @PostMapping(value = "/getConfigKey")
  public String  getConfigKey(@RequestBody @Validated Request<String> request) {
    return configService.selectConfigByKey(request.getData());
  }

  @ApiOperation("修改参数配置")
  @SaCheckPermission("system:config:edit")
  @Log(title = "参数管理", businessType = BusinessType.UPDATE)
  @PostMapping(value = "/edit")
  public void edit(@RequestBody @Validated(EditGroup.class) Request<SysConfigBo> request) {
    if (!configService.checkConfigKeyUnique(request.getData())) {
      fail("修改参数'" + request.getData().getConfigName() + "'失败，参数键名已存在");
    }
    configService.updateConfig(request.getData());
  }

  @ApiOperation("刷新参数缓存")
  @Log(title = "参数管理", businessType = BusinessType.CLEAN)
  @PostMapping("/refreshCache")
  public void refreshCache() {
    configService.resetConfigCache();
  }

}
