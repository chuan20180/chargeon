package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.vo.orders.OrdersSettleVo;
import com.obast.charer.system.service.business.IOrdersSettleManagerService;
import com.obast.charer.qo.OrdersSettleQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：订单结算管理
 */
@Api(tags = {"订单结算管理"})
@Slf4j
@RestController
@RequestMapping("/admin/ordersSettle")
public class OrdersSettleController {

    @Autowired
    private IOrdersSettleManagerService stationManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:charger_order_settle:list")
    @PostMapping("/list")
    public Paging<OrdersSettleVo> queryPageList(@Validated @RequestBody PageRequest<OrdersSettleQueryBo> pageRequest) {
        return stationManagerService.queryPageList(pageRequest);
    }
}
