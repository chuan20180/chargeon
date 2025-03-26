package com.obast.charer.system.inner;

import com.obast.charer.common.api.Response;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.enums.ChargePayTypeEnum;
import com.obast.charer.enums.ChargeStartTypeEnum;
import com.obast.charer.enums.ChargeStopTypeEnum;
import com.obast.charer.model.order.Orders;
import com.obast.charer.system.operate.IChargerOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：内部充电桩操作
 */
@Slf4j
@Api(tags = {"内部充电桩操作"})
@RestController
@RequestMapping("/inner/operate")
public class InnerOperateController {

    @Autowired
    private IChargerOperateService chargerOperateService;

    /**
     * 启动充电
     */
    @ApiOperation("启动充电")
    @GetMapping("/startCharge")
    public Response<?> startCharge(@RequestParam ChargePayTypeEnum chargePayType, @RequestParam ChargeStartTypeEnum chargeStartType, @RequestParam ChargeStopTypeEnum chargeStopType, @RequestParam String chargerDn, @RequestParam String chargerGunNo, @RequestParam String customerId, @RequestParam PlatformTypeEnum platform) {
        Orders orders = chargerOperateService.startCharge(chargePayType, chargeStartType, chargeStopType, chargerDn, chargerGunNo, customerId, platform);
        return Response.success(orders);
    }

    /**
     * 停止充电
     */
    @ApiOperation("停止充电")
    @GetMapping("/stopCharge")
    public Response<?> stopCharge(@RequestParam String orderId) {
        chargerOperateService.stopCharge(orderId);
        return Response.success();
    }
}
