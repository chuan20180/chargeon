package com.obast.charer.plugin.feign;

import com.obast.charer.api.PriceConfigBo;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import com.obast.charer.plugin.feign.factory.RemoteChargerFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 *
 * @author xueyi
 */
@FeignClient(
        contextId = "remoteChargerService",
        path = "/inner/charger",
        value = ServiceConstants.PLUGIN_SERVICE,
        fallbackFactory = RemoteChargerFallbackFactory.class
)
public interface IRemoteChargerService {

    @GetMapping(value = "/startCharge", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> startCharge(
            @RequestParam("chargerDn") String chargerDn,
            @RequestParam("chargerGunNo") String chargerGunNo,
            @RequestParam("orderTranId") String orderTranId,
            @RequestParam("logicalCardNo") String logicalCardNo,
            @RequestParam("physicalCardNo") String physicalCardNo,
            @RequestParam("balance") BigDecimal balance);

    @GetMapping(value = "/stopCharge", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> stopCharge(@RequestParam("chargerDn") String chargerDn, @RequestParam("chargerGunNo") String chargerGunNo);


    @GetMapping(value = "/balanceUpdate", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> balanceUpdate(@RequestParam("chargerDn") String chargerDn,
                              @RequestParam("chargerGunNo") String chargerGunNo,
                              @RequestParam("physicalCardNo") String physicalCardNo,
                              @RequestParam("balance") BigDecimal balance);


    @GetMapping(value = "/timingConfig", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> timingConfig(@RequestParam("chargerDn") String chargerDn);

    @GetMapping(value = "/paramConfig", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> paramConfig(@RequestParam("chargerDn") String chargerDn, @RequestParam("status") short status,  @RequestParam("maxPower") short maxPower);


    @PostMapping(value = "/priceConfig", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> priceConfig(@RequestBody PriceConfigBo bo);

    @GetMapping(value = "/reboot", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> reboot(@RequestParam("chargerDn") String chargerDn);

    @GetMapping(value = "/qrcodeConfig", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> qrcodeConfig(@RequestParam("chargerDn") String chargerDn);
}
