package com.obast.charer.openapi.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.openapi.dto.vo.OpenOrdersVo;
import com.obast.charer.openapi.service.IOpenOrdersService;
import com.obast.charer.qo.OrdersQueryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-充电订单"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/orders")
public class OpenOrdersController {

    @Autowired
    private IOpenOrdersService openOrderService;

    @ApiOperation("列表")
    @PostMapping("/list")
    public Paging<OpenOrdersVo> list(@RequestBody @Validated PageRequest<OrdersQueryBo> pageRequest) {
        String username = LoginHelper.getUserName();
        pageRequest.getData().setUserName(username);
        return openOrderService.queryPage(pageRequest);
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public OpenOrdersVo getDetail(@RequestBody @Validated Request<String> bo) {
        return openOrderService.queryDetail(bo.getData());
    }
}
