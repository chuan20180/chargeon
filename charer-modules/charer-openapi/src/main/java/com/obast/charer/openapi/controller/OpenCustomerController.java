package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.obast.charer.openapi.dto.vo.OpenCustomerVo;
import com.obast.charer.common.api.Request;
import com.obast.charer.openapi.service.IOpenCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-客户信息"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/customer")
public class OpenCustomerController {

    @Autowired
    private IOpenCustomerService openOrderService;

    @ApiOperation("详情")
    @PostMapping("/detail")
    public OpenCustomerVo getDetail(@RequestBody @Validated Request<String> bo) {
        return openOrderService.queryDetail(bo.getData());
    }
}
