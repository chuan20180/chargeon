package com.obast.charer.api.system.feign.factory;

import com.obast.charer.api.system.feign.IRemoteSmsService;
import com.obast.charer.common.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 充电柱控制服务
 *
 * @author xueyi
 */
@Slf4j
@Component
public class RemoteSmsFallbackFactory implements FallbackFactory<IRemoteSmsService> {

    @Override
    public IRemoteSmsService create(Throwable throwable) {
        log.error("插件服务调用失败:{},失败原因：", throwable.getMessage(), throwable);
        return new IRemoteSmsService() {

            @Override
            public Response<?> sendVerificationCode(String type, String mobile) {
                return Response.fail("发送验证码失败:" + throwable.getMessage());
            }

            @Override
            public Response<?> verifyVerificationCode(String type, String mobile, String code) {
                return Response.fail("验证验证码失败:" + throwable.getMessage());
            }
        };
    }
}
