package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "CustomerBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Customer.class, reverseConvertGenerate = false)
public class CustomerBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @NotNull(message = "用户名为空")
    private String userName;

    @NotNull(message = "手机号码为空")
    private String mobile;

    @NotNull(message = "用户昵称为空")
    private String nickName;

    @NotNull(message = "用户类型为空")
    private CustomerTypeEnum type;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    @JsonIgnore
    @JsonProperty
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @NotNull(message = "帐号状态为空")
    private EnableStatusEnum status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 备注
     */
    private String note;


}
