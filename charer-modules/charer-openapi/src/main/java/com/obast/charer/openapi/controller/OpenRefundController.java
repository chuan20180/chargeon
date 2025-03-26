package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.obast.charer.openapi.dto.vo.OpenRefundVo;
import com.obast.charer.qo.RefundQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.openapi.service.IOpenRefundService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-退款列表"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/refund")
public class OpenRefundController {

    @Autowired
    private IOpenRefundService openRefundService;

    @ApiOperation("列表")
    @PostMapping("/list")
    public Paging<OpenRefundVo> list(@RequestBody @Validated PageRequest<RefundQueryBo> pageRequest) {
        return openRefundService.queryPage(pageRequest);
    }
    
    @ApiOperation("详情")
    @PostMapping("/detail")
    public OpenRefundVo getDetail(@RequestBody @Validated Request<String> bo) {
        return openRefundService.queryDetail(bo.getData());
    }
}
