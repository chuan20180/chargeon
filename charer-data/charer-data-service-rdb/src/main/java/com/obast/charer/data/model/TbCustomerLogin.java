package com.obast.charer.data.model;

import com.obast.charer.common.enums.LoginTypeEnum;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.model.customer.CustomerLogin;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer_login")
@ApiModel(value = "客户登陆信息")
@AutoMapper(target = CustomerLogin.class)
public class TbCustomerLogin extends BaseTenantEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "登陆方式")
    @Convert(converter = LoginTypeEnum.Converter.class)
    private LoginTypeEnum loginType;

    @ApiModelProperty(value = "设备识别符")
    private String dn;

    @ApiModelProperty(value = "app操作系统")
    private String os;

    @ApiModelProperty(value = "app平台")
    @Convert(converter = PlatformTypeEnum.Converter.class)
    private PlatformTypeEnum platform;

    @ApiModelProperty(value = "App设备")
    private String device;

    @ApiModelProperty(value = "App操作系统版本")
    private String version;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间")
    private Date loginDate;
}
