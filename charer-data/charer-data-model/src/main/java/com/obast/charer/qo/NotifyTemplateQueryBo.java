package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.NotifyIdentifierEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotifyTemplateQueryBo extends BaseDto {

    private String name;

    private NotifyIdentifierEnum identifier;

    public EnableStatusEnum status;

}
