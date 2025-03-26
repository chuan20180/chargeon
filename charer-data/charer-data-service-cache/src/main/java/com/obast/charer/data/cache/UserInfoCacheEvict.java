package com.obast.charer.data.cache;

import com.obast.charer.common.constant.Constants;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class UserInfoCacheEvict {

    @CacheEvict(value = Constants.CACHE_USER_INFO, key = "#root.method.name+#uid")
    public void findByUid(String uid) {
    }

}
