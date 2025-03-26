package com.obast.charer.system.controller;

import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.vo.AppSettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：平台配置项管理
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/app")
@Api(tags = "参数配置 信息操作处理")
public class AppController extends BaseController {

  @Autowired
  private CharerProperties charerProperties;

  @ApiOperation("获取参数配置列表")
  @PostMapping("/setting")
  public AppSettingVo setting() {
      return new AppSettingVo(charerProperties, new HashMap<>());
  }
}
