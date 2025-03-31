package com.obast.charer.system.dto.vo.customer;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.AppOsEnum;
import com.obast.charer.enums.CustomerTypeEnum;
import com.obast.charer.model.BaseModel;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.obast.charer.model.customer.CustomerLogin;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomerVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CustomerLogin.class,convertGenerate = false)
public class CustomerLoginVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    private String customerId;

    private String dn;

    private String os;

    private String platform;

    private String device;

    private String version;

    private String language;

    private String loginIp;

    private Date loginDate;
}