package com.obast.charer.openapi.controller;

import com.obast.charer.common.api.Request;
import com.obast.charer.openapi.dto.vo.OpenPriceVo;
import com.obast.charer.openapi.service.IOpenPriceService;
import cn.dev33.satoken.annotation.SaIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-价格"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/price")
public class OpenPriceController {

    @Autowired
    private IOpenPriceService priceService;

    @SaIgnore
    @ApiOperation("详情")
    @PostMapping("/detail")
    public OpenPriceVo getDetail(@RequestBody @Validated Request<String> bo) {
        return priceService.queryDetail(bo.getData());
    }
}