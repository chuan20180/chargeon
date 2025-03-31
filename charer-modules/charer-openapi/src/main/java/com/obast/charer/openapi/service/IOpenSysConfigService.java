package com.obast.charer.openapi.service;


/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩订单服务接口
 */

public interface IOpenSysConfigService {

    /**
     * 根据key查询参数
     */
    Object selectByConfigKey(String key);


}
