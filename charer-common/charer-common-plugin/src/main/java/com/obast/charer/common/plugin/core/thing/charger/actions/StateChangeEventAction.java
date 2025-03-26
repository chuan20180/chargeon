package com.obast.charer.common.plugin.core.thing.charger.actions;

import com.obast.charer.common.plugin.core.thing.DeviceState;
import com.obast.charer.common.plugin.core.thing.charger.ChargerAction;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import lombok.*;
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
public class StateChangeEventAction<T> extends ChargerAction<T> {

    private DeviceState state;

    @Override
    public Enum<ChargerDirectiveEnum> getDirective() {
        return ChargerDirectiveEnum.STATE_CHANGE_EVENT;
    }


}
