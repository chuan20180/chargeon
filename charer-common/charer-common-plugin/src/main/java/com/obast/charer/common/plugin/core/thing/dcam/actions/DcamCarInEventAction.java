package com.obast.charer.common.plugin.core.thing.dcam.actions;

import com.obast.charer.common.plugin.core.thing.dcam.DcamAction;
import com.obast.charer.common.plugin.core.thing.dcam.DcamDirectiveEnum;
import com.obast.charer.common.model.DeviceImage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 车辆入场动作
 */
@EqualsAndHashCode(callSuper = true)
@Data

@SuperBuilder
@ToString(callSuper = true)
public class DcamCarInEventAction<T> extends DcamAction<T> {

    /**
     * 背景图片
     */
    private List<DeviceImage> bgImages;

    /**
     * 特征图片
     */
    private List<DeviceImage> fetureImages;

    @Override
    public Enum<DcamDirectiveEnum> getDirective() {
        return DcamDirectiveEnum.CAR_IN_EVENT;
    }

}

