package com.obast.charer.model.parking;

import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parking extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String stationId;

    private I18nField name;

    private String no;

    private String note;

}
