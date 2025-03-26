package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.obast.charer.common.api.Request;
import com.obast.charer.openapi.service.IOpenPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-场站活动列表"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/promotion")
public class OpenPromotionController {

    @Autowired
    private IOpenPromotionService openPromotionService;
    @SaIgnore
    @ApiOperation("详情")
    @PostMapping("/detail")
    public Object getDetail(@RequestBody @Validated Request<String> bo) {
        return openPromotionService.queryDetail(bo.getData());
    }
}