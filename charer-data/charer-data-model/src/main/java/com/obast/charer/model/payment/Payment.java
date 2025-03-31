package com.obast.charer.model.payment;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseModel implements Id<String>, Serializable {

    private String id;

    private I18nField name;

    private PaymentIdentifierEnum identifier;

    private I18nField description;

    private String no;

    private EnableStatusEnum status;

    private Integer isDefault;

    private String img;

    private String note;

    private String properties;
}
