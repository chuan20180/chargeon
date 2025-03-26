package com.obast.charer.api.system.feign.factory;

import com.obast.charer.common.api.Response;
import com.obast.charer.api.system.feign.IRemoteOrderService;
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
public class RemoteOrderFallbackFactory implements FallbackFactory<IRemoteOrderService> {

    @Override
    public IRemoteOrderService create(Throwable throwable) {
        log.error("订单服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemoteOrderService() {
            @Override
            public Response<?> settle(String orderId) {
                return Response.fail("订单结算失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> deal(String orderId) {
                return Response.fail("订单结算失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> notify(String orderId) {
                return Response.fail("订单结算失败:" + throwable.getMessage());
            }
        };
    }
}
