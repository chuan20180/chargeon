package com.obast.charer.system.listener.event;

import com.obast.charer.model.order.Orders;
import com.obast.charer.push.wechat.WechatPushConfig;
import com.obast.charer.push.wechat.WechatPushMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

/**
 * 结算完成事件
 **/

@Data
@AllArgsConstructor
public class OrderSettledEvent implements Serializable {

    private Orders order;

}
