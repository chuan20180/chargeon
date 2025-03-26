package com.obast.charer.model.system;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysCountry extends TenantModel implements Id<String>, Serializable {

    private String id;

    private I18nField name;

    private String iso2;

    private String iso3;

    private String tel;

    private String telRule;

    private String icon;

    private EnableStatusEnum status;

    private String note;
}
