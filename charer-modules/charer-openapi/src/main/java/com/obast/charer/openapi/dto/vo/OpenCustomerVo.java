package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.customer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenCustomerVo")
@Data
@AutoMapper(target = Customer.class,convertGenerate = false)
public class OpenCustomerVo extends BaseModel {

    private static final long serialVersionUID = -1L;

    private String id;

    private String tenantId;

    private String wxOpenId;

    private String userName;

    private String nickName;

    private CustomerTypeEnum type;

    private String mobile;

    private String sex;

    private String avatar;

    @JsonIgnore
    @JsonProperty
    private String password;

    private EnableStatusEnum status;

    private String loginIp;

    private Date loginDate;

    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal topupAmount;

    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal balanceAmount;

    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settledAmount;

    private String note;

}
