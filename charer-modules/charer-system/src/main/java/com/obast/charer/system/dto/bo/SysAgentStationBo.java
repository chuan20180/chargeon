package com.obast.charer.system.dto.bo;

import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.system.SysAgentStation;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = SysAgentStation.class)
public class SysAgentStationBo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private String id;

    @NotBlank(message = "代理商不能为空", groups = {AddGroup.class, EditGroup.class})
    private String agentId;

    @NotBlank(message = "场站不能为空", groups = {AddGroup.class, EditGroup.class})
    private String stationId;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    private String note;
}
