package com.obast.charer.common.plugin.core.thing.dcam.actions;

import com.obast.charer.common.plugin.core.thing.dcam.DcamAction;
import com.obast.charer.common.plugin.core.thing.dcam.DcamDirectiveEnum;
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
public class DcamRegisterEventAction<T> extends DcamAction<T> {

    private String username;
    private String password;

    @Override
    public Enum<DcamDirectiveEnum> getDirective() {
        return DcamDirectiveEnum.REGISTER_EVENT;
    }
}
