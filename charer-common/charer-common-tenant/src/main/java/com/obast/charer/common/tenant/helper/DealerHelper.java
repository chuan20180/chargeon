package com.obast.charer.common.tenant.helper;

import com.obast.charer.common.satoken.util.LoginHelper;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 分销商助手
 *
 * @author Lion Li
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DealerHelper {


    /**
     * 是否忽略租户
     */
    private static final ThreadLocal<Boolean> IGNORE = new TransmittableThreadLocal<>();
    /**
     * 租户功能是否启用
     */
    public static boolean isEnable() {
        return true;
    }

    /**
     * 获取当前分销商id
     */
    public static String getDealerId() {
        return LoginHelper.getDealerId();
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
        IGNORE.remove();
    }

}
