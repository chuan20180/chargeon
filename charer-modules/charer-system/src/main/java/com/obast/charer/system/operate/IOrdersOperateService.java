package com.obast.charer.system.operate;

import com.obast.charer.model.order.Orders;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface IOrdersOperateService {



    void settle(String orderId, String note);


    void deal(String orderId);

    void notify(String orderId);

}
