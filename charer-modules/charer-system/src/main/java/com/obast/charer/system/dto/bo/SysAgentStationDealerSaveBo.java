package com.obast.charer.system.dto.bo;

import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


@Data
public class SysAgentStationDealerSaveBo implements Serializable {
    private static final long serialVersionUID = 1L;


    @NotBlank(message = "代理商场站不能为空", groups = {AddGroup.class, EditGroup.class})
    private String agentStationId;

    @NotBlank(message = "合作商不能为空", groups = {AddGroup.class, EditGroup.class})
    private List<SysAgentStationDealerBo> dealers;


    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    private String note;
}
