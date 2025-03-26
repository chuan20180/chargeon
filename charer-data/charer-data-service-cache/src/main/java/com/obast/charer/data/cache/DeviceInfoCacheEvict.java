package com.obast.charer.data.cache;

import com.obast.charer.common.constant.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class DeviceInfoCacheEvict {

    @CacheEvict(value = Constants.CACHE_DEVICE_INFO, key = "#root.method.name+#deviceId")
    public void findByDeviceId(String deviceId) {
    }

    @CacheEvict(value = Constants.CACHE_DEVICE_INFO, key = "#root.method.name+#deviceName")
    public void findByDeviceName(String deviceName) {
    }

}
