package com.obast.charer.api.system.feign.factory;

import com.obast.charer.api.system.feign.IRemoteTopupService;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.model.PaymentNotify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 充值服务
 */
@Slf4j
@Component
public class RemoteTopupFallbackFactory implements FallbackFactory<IRemoteTopupService> {

    @Override
    public IRemoteTopupService create(Throwable throwable) {
        log.error("充值服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemoteTopupService() {
            @Override
            public Response<?> complete(PaymentNotify paymentNotify) {
                return Response.fail("充值失败:" + throwable.getMessage());
            }
        };
    }
}
