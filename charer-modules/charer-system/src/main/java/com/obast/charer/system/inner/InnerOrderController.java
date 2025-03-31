package com.obast.charer.system.inner;

import com.obast.charer.common.api.Response;
import com.obast.charer.system.operate.IOrdersOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Slf4j
@Api(tags = {"内部充电订单"})
@RestController
@RequestMapping("/inner/order")
public class InnerOrderController {

    @Autowired
    private IOrdersOperateService ordersOperateService;

    /**
     * 订单结算
     *
     * @param orderId 订单id
     */

    @ApiOperation("订单结算")
    @GetMapping("/settle")
    public Response<?> settle(@RequestParam("orderId") String orderId) {
        ordersOperateService.settle(orderId, "");
        return Response.success();
    }

    @ApiOperation("订单通知")
    @PostMapping("/notify")
    public void notify(@RequestParam("orderId") String orderId) {
        ordersOperateService.notify(orderId);
    }
}
