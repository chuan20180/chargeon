package com.obast.charer.api.system.feign.factory;

import com.obast.charer.api.system.feign.IRemoteOrderService;
import com.obast.charer.api.system.feign.IRemoteParkService;
import com.obast.charer.common.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 登录服务 | 管理端 降级处理
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteParkFallbackFactory implements FallbackFactory<IRemoteParkService> {

    @Override
    public IRemoteParkService create(Throwable throwable) {
        log.error("占位服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemoteParkService() {
            @Override
            public Response<?> settle(String orderId) {
                return Response.fail("占位结算失败:" + throwable.getMessage());
            }

        };
    }
}
