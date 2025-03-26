package com.obast.charer.data.cache;

import com.obast.charer.common.constant.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class ThingModelCacheEvict {

    @CacheEvict(value = Constants.CACHE_THING_MODEL, key = "#root.method.name+#s")
    public void findById(Long s) {
    }

    @CacheEvict(value = Constants.CACHE_THING_MODEL, key = "#root.method.name+#productKey")
    public void findByProductKey(String productKey){
    }

}
