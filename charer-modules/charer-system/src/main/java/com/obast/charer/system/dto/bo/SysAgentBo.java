package com.obast.charer.system.dto.bo;

import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.system.SysAgent;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = SysAgent.class, reverseConvertGenerate = false)
public class SysAgentBo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private String id;

    @NotBlank(message = "代理名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactMobile;

    @ApiModelProperty(value = "登陆用户名")
    private String userName;

    @ApiModelProperty(value = "登陆密码")
    private String password;

    @ApiModelProperty(value = "运营商分成比例")
    private BigDecimal tenantProfitPercent;

    @ApiModelProperty(value = "关联菜单id")
    private List<String> menuIds;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    private String note;

}
