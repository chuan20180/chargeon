package com.obast.charer.api.system.feign.factory;

import com.obast.charer.api.system.feign.IRemoteRefundService;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.model.RefundNotify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 充值服务
 */
@Slf4j
@Component
public class RemoteRefundFallbackFactory implements FallbackFactory<IRemoteRefundService> {

    @Override
    public IRemoteRefundService create(Throwable throwable) {
        log.error("退款服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemoteRefundService() {
            @Override
            public Response<?> complete(RefundNotify refundNotify) {
                return Response.fail("退款失败:" + throwable.getMessage());
            }
        };
    }
}
