package com.obast.charer.system.dto.vo.device;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChargerGunCurrentEnum;
import com.obast.charer.enums.ChargerGunSpeedEnum;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.device.ChargerGun;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ChargerGun")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ChargerGun.class,convertGenerate = false)
public class ChargerGunVo extends BaseDto {

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "充电柱id")
    @ExcelProperty(value = "充电柱id")
    private String chargerId;

    @ApiModelProperty(value = "车位id")
    @ExcelProperty(value = "车位id")
    private String parkingId;

    @ApiModelProperty(value = "充电枪号")
    @ExcelProperty(value = "充电枪号")
    private String no;

    @ApiModelProperty(value = "是否归位")
    @ExcelProperty(value = "是否归位")
    private Integer back;

    @ApiModelProperty(value = "是否插枪")
    @ExcelProperty(value = "是否插枪")
    private Integer slot;

    @ApiModelProperty(value = "速度")
    private ChargerGunSpeedEnum speed;

    @ApiModelProperty(value = "类型")
    private ChargerGunCurrentEnum current;

    @ApiModelProperty(value = "类型")
    private Integer power;

    @ApiModelProperty(value = "枪状态")
    @ExcelProperty(value = "枪状态")
    private ChargerGunStateEnum state;
}
