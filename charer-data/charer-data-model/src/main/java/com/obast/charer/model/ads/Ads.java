package com.obast.charer.model.ads;

import com.obast.charer.enums.AdsTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
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
public class Ads extends BaseModel implements Id<String>, Serializable {

    private String id;

    private I18nField name;

    private AdsTypeEnum type;

    private I18nField content;

    private String link;

    private String img;

    private String note;

    private EnableStatusEnum status;
}
