package com.obast.charer.common.tenant.helper;

import com.obast.charer.common.constant.GlobalConstants;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.common.utils.StringUtils;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 租户助手
 *
 * @author Lion Li
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantHelper {

    private static final String DYNAMIC_TENANT_KEY = GlobalConstants.GLOBAL_REDIS_KEY + "dynamicTenant";

    private static final ThreadLocal<String> TEMP_DYNAMIC_TENANT = new TransmittableThreadLocal<>();

    /**
     * 是否忽略租户
     */
    private static final ThreadLocal<Boolean> IGNORE = new TransmittableThreadLocal<>();
    /**
     * 租户功能是否启用
     */
    public static boolean isEnable() {
        return Convert.toBool(SpringUtils.getProperty("tenant.enable"), false);
    }


    /**
     * 设置动态租户(一直有效 需要手动清理)
     * <p>
     * 如果为非web环境 那么只在当前线程内生效
     */
    public static void setDynamic(String tenantId) {
        if (!SpringMVCUtil.isWeb()) {
            TEMP_DYNAMIC_TENANT.set(tenantId);
            return;
        }
        String cacheKey = DYNAMIC_TENANT_KEY + ":" + LoginHelper.getUserId();
        RedisUtils.setCacheObject(cacheKey, tenantId);
        SaHolder.getStorage().set(cacheKey, tenantId);
    }

    /**
     * 获取动态租户(一直有效 需要手动清理)
     * <p>
     * 如果为非web环境 那么只在当前线程内生效
     */
    public static String getDynamic() {
        if (!SpringMVCUtil.isWeb()) {
            return TEMP_DYNAMIC_TENANT.get();
        }
        String cacheKey = DYNAMIC_TENANT_KEY + ":" + LoginHelper.getUserId();
        String tenantId = (String) SaHolder.getStorage().get(cacheKey);
        if (StringUtils.isEmpty(tenantId)) {
            return tenantId;
        }
        tenantId = RedisUtils.getCacheObject(cacheKey);
        SaHolder.getStorage().set(cacheKey, tenantId);
        return tenantId;
    }

    /**
     * 清除动态租户
     */
    public static void clearDynamic() {
        if (!SpringMVCUtil.isWeb()) {
            TEMP_DYNAMIC_TENANT.remove();
            return;
        }
        String cacheKey = DYNAMIC_TENANT_KEY + ":" + LoginHelper.getUserId();
        RedisUtils.deleteObject(cacheKey);
        SaHolder.getStorage().delete(cacheKey);
    }

    /**
     * 获取当前租户id(动态租户优先)
     */
    public static String getTenantId() {
        String tenantId = TenantHelper.getDynamic();
        if (Objects.isNull(tenantId)) {
            tenantId = LoginHelper.getTenantId();
        }
        return tenantId;
    }

    public static void setIgnore(Boolean ignore) {
        IGNORE.set(ignore);
    }


    /**
     * 开启忽略租户(开启后需手动调用 {@link #disableIgnore()} 关闭)
     */
    public static void enableIgnore() {
        IGNORE.set(Boolean.TRUE);
    }

    /**
     * 关闭忽略租户
     */
    public static void disableIgnore() {
        IGNORE.remove();
    }

    /**
     * 在忽略租户中执行
     *
     * @param handle 处理执行方法
     */
    public static void ignore(Runnable handle) {
        enableIgnore();
        try {
            handle.run();
        } finally {
            disableIgnore();
        }
    }

    /**
     * 在忽略租户中执行
     *
     * @param handle 处理执行方法
     */
    public static <T> T ignore(Supplier<T> handle) {
        enableIgnore();
        try {
            return handle.get();
        } finally {
            disableIgnore();
        }
    }

    /**
     * 当前是否忽略租户
     *
     * @return 是否忽略
     */
    public static boolean isIgnore() {
        Boolean aBoolean = IGNORE.get();
        return Boolean.TRUE.equals(aBoolean);
    }


    public static void clear() {
        TEMP_DYNAMIC_TENANT.remove();
        IGNORE.remove();
    }

}
