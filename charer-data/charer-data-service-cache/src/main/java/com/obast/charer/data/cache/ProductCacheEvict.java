package com.obast.charer.data.cache;

import com.obast.charer.common.constant.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class ProductCacheEvict {

    @CacheEvict(value = Constants.CACHE_PRODUCT, key = "#root.method.name+#id")
    public void findById(String id) {
    }

    @CacheEvict(value = Constants.CACHE_PRODUCT, key = "#root.method.name+#productKey")
    public void findByProductKey(String productKey) {
    }

}
