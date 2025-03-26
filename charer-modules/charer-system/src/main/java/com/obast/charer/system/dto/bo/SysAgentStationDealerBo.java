package com.obast.charer.system.dto.bo;

import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.system.SysAgentStationDealer;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = SysAgentStationDealer.class)
public class SysAgentStationDealerBo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private String id;

    @NotBlank(message = "代理商不能为空", groups = {AddGroup.class, EditGroup.class})
    private String agentId;

    @NotBlank(message = "合作商不能为空", groups = {AddGroup.class, EditGroup.class})
    private String dealerId;

    private BigDecimal percent;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    private String note;
}
