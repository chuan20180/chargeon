package com.obast.charer.common.plugin.core.thing.charger.actions;

import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.charger.ChargerAction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 设备登陆动作
 *
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@ToString(callSuper = true)
public class LoginAckAction<T> extends ChargerAction<T> {

    @Override
    public Enum<ChargerDirectiveEnum> getDirective() {
        return ChargerDirectiveEnum.LOGIN_ACK;
    }
}
