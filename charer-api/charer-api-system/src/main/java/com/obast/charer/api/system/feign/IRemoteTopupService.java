package com.obast.charer.api.system.feign;

import com.obast.charer.api.system.feign.factory.RemoteTopupFallbackFactory;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import com.obast.charer.common.model.PaymentNotify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 充值
 */
@FeignClient(
        contextId = "remoteTopupService",
        path = "/inner/topup",
        value = ServiceConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteTopupFallbackFactory.class
)
public interface IRemoteTopupService {

    /**
     * 充值完成
     */
    @PostMapping(value = "/complete", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> complete(@RequestBody PaymentNotify paymentNotify);

}
