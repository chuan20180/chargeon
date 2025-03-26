package com.obast.charer.api.system.feign;

import com.obast.charer.api.system.feign.factory.RemoteSmsFallbackFactory;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author xueyi
 */
@FeignClient(
        contextId = "remoteSmsService",
        path = "/inner/sms",
        value = ServiceConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteSmsFallbackFactory.class
)
public interface IRemoteSmsService {

    @PostMapping(value = "/sendVerificationCode", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> sendVerificationCode(@RequestParam("type") String type, @RequestParam("mobile") String mobile);

    @PostMapping(value = "/verifyVerificationCode", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> verifyVerificationCode(@RequestParam("type") String type, @RequestParam("mobile") String mobile, @RequestParam("code") String code);
}
