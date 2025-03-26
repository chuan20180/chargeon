package com.obast.charer.system.operate;

import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.enums.NotifyIdentifierEnum;

import java.util.Map;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface INotifyOperateService {

    void sendNotify(NotifyIdentifierEnum identifier, PlatformTypeEnum platform, Map<String, Object> params);


}
