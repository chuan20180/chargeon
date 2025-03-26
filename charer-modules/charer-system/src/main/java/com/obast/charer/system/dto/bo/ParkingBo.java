package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.parking.Parking;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


@ApiModel(value = "ParkingBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Parking.class, reverseConvertGenerate = false)
public class ParkingBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    private String stationId;

    private String parkingId;

    private I18nField name;

    private String no;

    private String note;
}
