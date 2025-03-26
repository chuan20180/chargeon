package com.obast.charer.common.plugin.core.thing.dcam.actions;

import com.obast.charer.common.plugin.core.thing.DeviceState;
import com.obast.charer.common.plugin.core.thing.dcam.DcamAction;
import com.obast.charer.common.plugin.core.thing.dcam.DcamDirectiveEnum;
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
public class DcamStateEventAction<T> extends DcamAction<T> {

    private DeviceState state;

    @Override
    public Enum<DcamDirectiveEnum> getDirective() {
        return DcamDirectiveEnum.STATE_EVENT;
    }


}
