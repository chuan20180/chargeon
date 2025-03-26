package com.obast.charer.common.plugin.core.thing.dcam.message;

import com.obast.charer.common.api.BaseMessage;
import com.obast.charer.common.api.IMessage;
import lombok.*;

import java.util.Date;

/**
 * 数据包
 *
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DcamCarInEventMessage extends BaseMessage implements IMessage {

    /**
     * 车牌颜色
     */
    private Integer plateColor;

    /**
     * 车牌颜色
     */
    private Integer plateType;

    /**
     * 车牌
     */
    private String plate;

    /**
     * 车位id
     */
    private Integer zoneId;


    /**
     * 车位名称
     */
    private String zoneName;

    /**
     * 时间
     */
    private Date time;


    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
