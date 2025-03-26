package com.obast.charer.plugin.feign.factory;

import com.obast.charer.api.PriceConfigBo;
import com.obast.charer.common.api.Response;
import com.obast.charer.plugin.feign.IRemoteChargerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 充电柱控制服务
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteChargerFallbackFactory implements FallbackFactory<IRemoteChargerService> {

    @Override
    public IRemoteChargerService create(Throwable throwable) {
        log.error("充电桩服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemoteChargerService() {
            @Override
            public Response<?> startCharge(String chargerDn, String chargerGunNo, String orderTranId, String logicalCardNo, String physicalCardNo, BigDecimal balance) {
                return Response.fail("启动充电失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> stopCharge(String  chargerDn, String chargerGunNo) {
                return Response.fail("停止充电失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> balanceUpdate(String chargerDn, String chargerGunNo, String physicalCardNo, BigDecimal balance) {
                return Response.fail("余额更新失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> timingConfig(String chargerDn) {
                return Response.fail("校时下发失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> paramConfig(String chargerDn, short status, short maxPower) {
                return Response.fail("参数下发失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> priceConfig(PriceConfigBo bo) {
                return Response.fail("计费模型下发失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> reboot(String chargerDn) {
                return Response.fail("重起失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> qrcodeConfig(String chargerDn) {
                return Response.fail("二维码下发失败:" + throwable.getMessage());
            }
        };
    }
}
