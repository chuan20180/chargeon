package com.obast.charer.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.customer.Customer;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer")
@ApiModel(value = "客户")
@AutoMapper(target = Customer.class)
public class TbCustomer extends BaseEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户类型")
    @Convert(converter = CustomerTypeEnum.Converter.class)
    private CustomerTypeEnum type;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户性别")
    private String sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    @JsonProperty
    private String password;

    @ApiModelProperty(value = "帐号状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间")
    private Date loginDate;

    @ApiModelProperty(value = "逻辑卡号")
    private String logicalCardNo;

    @ApiModelProperty(value = "物理卡号")
    private String physicalCardNo;

    @ApiModelProperty(value = "客户余额")
    private BigDecimal balanceAmount;

    @ApiModelProperty(value = "赠送余额")
    private BigDecimal giveAmount;

    @ApiModelProperty(value = "额度余额")
    @Type(type = "json")
    private List<Customer.Quota> quotaAmount;

    @ApiModelProperty(value = "备注")
    private String note;

}
