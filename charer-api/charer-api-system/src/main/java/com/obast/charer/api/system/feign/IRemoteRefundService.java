package com.obast.charer.api.system.feign;

import com.obast.charer.api.system.feign.factory.RemoteRefundFallbackFactory;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import com.obast.charer.common.model.RefundNotify;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 充值
 */
@FeignClient(
        contextId = "remoteRefundService",
        path = "/inner/refund",
        value = ServiceConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteRefundFallbackFactory.class
)
public interface IRemoteRefundService {

    /**
     * 充值完成
     */
    @PostMapping(value = "/complete", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> complete(@RequestBody RefundNotify refundNotify);
}
