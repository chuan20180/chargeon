package com.obast.charer.plugin.controller.inner;

import com.obast.charer.api.PriceConfigBo;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.plugin.service.IChargerInvokeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Slf4j
@Api(tags = {"内部充电桩控制"})
@RestController
@RequestMapping("/inner/charger")
public class InnerChargerController {

    @Autowired
    private IChargerInvokeService chargerInvokeService;

    @ApiOperation("启动充电")
    @GetMapping("/startCharge")
    Response<?> startCharge(@RequestParam("chargerDn") String chargerDn,
                            @RequestParam("chargerGunNo") String chargerGunNo,
                            @RequestParam("orderTranId") String orderTranId,
                            @RequestParam("logicalCardNo") String logicalCardNo,
                            @RequestParam("physicalCardNo") String physicalCardNo,
                            @RequestParam("balance") BigDecimal balance) {
        ActionResult<?> result = chargerInvokeService.startCharge(chargerDn, chargerGunNo, orderTranId, logicalCardNo, physicalCardNo, balance);
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }

    @ApiOperation("停止充电")
    @GetMapping("/stopCharge")
    Response<?> stopCharge(@RequestParam("chargerDn") String chargerDn, @RequestParam("chargerGunNo") String chargerGunNo){
        ActionResult<?> result = chargerInvokeService.stopCharge(chargerDn, chargerGunNo);
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }


    @ApiOperation("余额更新")
    @GetMapping("/balanceUpdate")
    Response<?> balanceUpdate(@RequestParam("chargerDn") String chargerDn,
                              @RequestParam("chargerGunNo") String chargerGunNo,
                              @RequestParam("physicalCardNo") String physicalCardNo,
                              @RequestParam("balance") BigDecimal balance){
        ActionResult<?> result = chargerInvokeService.balanceUpdate(chargerDn, chargerGunNo, physicalCardNo, balance);
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }


    @ApiOperation("校时")
    @GetMapping("/timingConfig")
    Response<?> timingConfig(@RequestParam("chargerDn") String chargerDn){
        ActionResult<?> result = chargerInvokeService.timingConfig(chargerDn);
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }


    @ApiOperation("参数配置")
    @GetMapping("/paramConfig")
    Response<?> paramConfig(@RequestParam("chargerDn") String chargerDn, @RequestParam("status") short status,  @RequestParam("maxPower") short maxPower){
        ActionResult<?> result = chargerInvokeService.paramConfig(chargerDn, status, maxPower);
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }

    @ApiOperation("下发计价规则")
    @PostMapping("/priceConfig")
    Response<?> priceConfig(@RequestBody PriceConfigBo bo){
        ActionResult<?> result = chargerInvokeService.priceConfig(bo.getChargerDn(), bo.getPrice());
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }

    @ApiOperation("重起")
    @GetMapping("/reboot")
    Response<?> reboot(@RequestParam("chargerDn") String chargerDn){
        ActionResult<?> result = chargerInvokeService.reboot(chargerDn);
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }

    @ApiOperation("下发二维码")
    @GetMapping("/qrcodeConfig")
    Response<?> qrcodeConfig(@RequestParam("chargerDn") String chargerDn){
        ActionResult<?> result = chargerInvokeService.qrcodeConfig(chargerDn);
        if(result.getCode() == 0) {
            return Response.success();
        }
        return Response.fail(result.getReason());
    }

}
