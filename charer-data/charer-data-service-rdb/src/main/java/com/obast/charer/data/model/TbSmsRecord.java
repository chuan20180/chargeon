package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.model.sms.SmsRecord;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sms_record")
@AutoMapper(target = SmsRecord.class)
public class TbSmsRecord extends BaseTenantEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "参数主键")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "手机号码")
    public String mobile;

    @ApiModelProperty(value = "短信内容")
    public String message;

    @ApiModelProperty(value = "发送结果,0成功,1失败")
    public Integer result;

    @ApiModelProperty(value = "短信服务提供商")
    public String identifier;

    @ApiModelProperty(value = "短信类型")
    public String type;

}
