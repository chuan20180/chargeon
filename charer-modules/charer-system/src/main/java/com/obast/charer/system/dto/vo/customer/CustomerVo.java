package com.obast.charer.system.dto.vo.customer;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.customer.Customer;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomerVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Customer.class,convertGenerate = false)
public class CustomerVo extends BaseDto {

    private String id;

    private String tenantId;

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

    //充值金额
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal topupAmount;

    //已消费金额
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal settleAmount;

    //余额
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal balanceAmount;

    //服务费余额
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal giveAmount;

    //服务费额度余额
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal quotaTotalAmount;

    //服务费额度
    private List<Customer.Quota> quotaAmount;

    private String note;

    //已退款金额
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal refundedAmount;

    //可退款金额
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal availableRefundAmount;
}