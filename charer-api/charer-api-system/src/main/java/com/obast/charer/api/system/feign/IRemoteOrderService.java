package com.obast.charer.api.system.feign;

import com.obast.charer.api.system.feign.factory.RemoteOrderFallbackFactory;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author xueyi
 */
@FeignClient(
        contextId = "remoteOrderService",
        path = "/inner/order",
        value = ServiceConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteOrderFallbackFactory.class
)
public interface IRemoteOrderService {

    /**
     * 订单结算
     *
     * @param orderId 订单id
     * @return 结果
     */
    @GetMapping(value = "/settle", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> settle(@RequestParam("orderId") String orderId);

    /**
     * 订单分成
     *
     * @param orderId 订单id
     * @return 结果
     */
    @GetMapping(value = "/deal", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> deal(@RequestParam("orderId") String orderId);

    /**
     * 订单通知
     *
     * @param orderId 订单id
     * @return 结果
     */
    @GetMapping(value = "/notify", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> notify(@RequestParam("orderId") String orderId);

}
