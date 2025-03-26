package com.obast.charer.api.system.feign;

import com.obast.charer.api.system.feign.factory.RemoteOperateFallbackFactory;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.enums.ChargePayTypeEnum;
import com.obast.charer.enums.ChargeStartTypeEnum;
import com.obast.charer.enums.ChargeStopTypeEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author xueyi
 */
@FeignClient(
        contextId = "remoteOperateService",
        path = "/inner/operate",
        value = ServiceConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteOperateFallbackFactory.class
)
public interface IRemoteOperateService {

    /**
     * 启动充电
     */
    @GetMapping(value = "/startCharge", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> startCharge(@RequestParam ChargePayTypeEnum chargePayType, @RequestParam ChargeStartTypeEnum chargeStartType, @RequestParam ChargeStopTypeEnum chargeStopType, @RequestParam String chargerDn, @RequestParam String chargerGunNo, @RequestParam String customerId, @RequestParam PlatformTypeEnum platform );

    /**
     * 停止充电
     */
    @GetMapping(value = "/stopCharge", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> stopCharge(@RequestParam String orderId);
}
