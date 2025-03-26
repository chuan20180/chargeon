package com.obast.charer.common.plugin.core.thing.charger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * @ Author：chuan
 * @ Date：2024-09-08-14:15
 * @ Version：1.0
 * @ Description：dd
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class DeviceInvoke<T> extends DeviceAction<T> {

    /**
     * 服务名
     */
    private String name;

    /**
     * 序号
     */
    private short serial;


    /**
     * 服务参数
     */
    private Map<String, ?> params;

    /**
     * 配置信息
     */
    private Map<String, ?> config;


    protected T data;

}