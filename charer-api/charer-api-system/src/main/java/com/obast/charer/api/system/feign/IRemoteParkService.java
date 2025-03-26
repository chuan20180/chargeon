package com.obast.charer.api.system.feign;

import com.obast.charer.api.system.feign.factory.RemoteOrderFallbackFactory;
import com.obast.charer.api.system.feign.factory.RemoteParkFallbackFactory;
import com.obast.charer.common.api.Response;
import com.obast.charer.common.constant.SecurityConstants;
import com.obast.charer.common.constant.ServiceConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author xueyi
 */
@FeignClient(
        contextId = "remoteParkService",
        path = "/inner/park",
        value = ServiceConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteParkFallbackFactory.class
)
public interface IRemoteParkService {

    /**
     * 占位费结算
     */
    @GetMapping(value = "/settle", headers = SecurityConstants.FROM_SOURCE_INNER)
    Response<?> settle(@RequestParam("parkId") String parkId);

}
