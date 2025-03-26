package com.obast.charer.common.plugin.core.thing.charger.actions;

import com.obast.charer.common.plugin.core.thing.charger.ChargerAction;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 设备在线状态变更
 *
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Data

@SuperBuilder
@ToString(callSuper = true)
public class PingEventAction<T> extends ChargerAction<T> {

    @Override
    public Enum<ChargerDirectiveEnum> getDirective() {
        return ChargerDirectiveEnum.PING_EVENT;
    }

}
