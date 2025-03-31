package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.excel.utils.ExcelUtil;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.OrderSettlementBo;
import com.obast.charer.system.dto.vo.orders.OrdersVo;
import com.obast.charer.system.operate.IChargerOperateService;
import com.obast.charer.system.service.business.IOrdersManagerService;
import com.obast.charer.qo.OrdersQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.system.operate.IOrdersOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理
 */
@Api(tags = {"充电订单"})
@Slf4j
@RestController
@RequestMapping("/admin/order")
public class OrdersController {

    @Autowired
    private IOrdersManagerService ordersManagerService;

    @Autowired
    private IOrdersOperateService ordersOperateService;

    @Autowired
    private IChargerOperateService chargerOperateService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:order:list")
    @PostMapping("/list")
    public Paging<OrdersVo> list(@Validated @RequestBody PageRequest<OrdersQueryBo> pageRequest) {
        return ordersManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "结算", notes = "订单结算", httpMethod = "POST")
    @SaCheckPermission("business:order:settle")
    @PostMapping("/settle")
    public void settlement(@Validated @RequestBody PageRequest<OrderSettlementBo> request) {
        OrderSettlementBo bo = request.getData();
        ordersOperateService.settle(bo.getId(), bo.getNote());
    }

    @ApiOperation(value = "通知", notes = "通知", httpMethod = "POST")
    @PostMapping("/notify")
    public void notify(@Validated @RequestBody PageRequest<OrderSettlementBo> request) {
        OrderSettlementBo bo = request.getData();
        ordersOperateService.notify(bo.getId());
    }

    @ApiOperation("停止充电")
    @SaCheckPermission("business:order:operate")
    @PostMapping("/stopCharge")
    public void stopCharge(@Validated @RequestBody PageRequest<String> request) {
        chargerOperateService.stopCharge(request.getData());
    }

    @ApiOperation("导出列表")
    @Log(title = "充电订单", businessType = BusinessType.EXPORT)
    @SaCheckPermission("business:order:export")
    @PostMapping("/export")
    public void export(@RequestBody PageRequest<OrdersQueryBo> pageRequest, HttpServletResponse response) {
        List<OrdersVo> list = ordersManagerService.queryList(pageRequest);
        ExcelUtil.exportExcel(list, "充电订单", OrdersVo.class, response);
    }
}