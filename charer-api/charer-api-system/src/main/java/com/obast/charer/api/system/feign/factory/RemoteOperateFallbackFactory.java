package com.obast.charer.api.system.feign.factory;

import com.obast.charer.api.system.feign.IRemoteOperateService;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.enums.ChargePayTypeEnum;
import com.obast.charer.enums.ChargeStartTypeEnum;
import com.obast.charer.enums.ChargeStopTypeEnum;
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
public class RemoteOperateFallbackFactory implements FallbackFactory<IRemoteOperateService> {

    @Override
    public IRemoteOperateService create(Throwable throwable) {
        log.error("操作设备失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemoteOperateService() {
            @Override
            public Response<?> startCharge(ChargePayTypeEnum chargePayType, ChargeStartTypeEnum chargeStartType, ChargeStopTypeEnum chargeStopType, String chargerDn, String chargerGunNo, String customerId, PlatformTypeEnum platform) {
                return Response.fail("启动充电失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> stopCharge(String orderId) {
                return Response.fail("停止充电失败:" + throwable.getMessage());
            }

        };
    }
}
