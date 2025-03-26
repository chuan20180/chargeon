package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseTenantEntity;
import com.obast.charer.enums.MessageStateEnum;
import com.obast.charer.enums.NotifyScopeEnum;
import com.obast.charer.enums.NotifyTypeEnum;
import com.obast.charer.model.customer.CustomerNotify;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customer_notify")
@ApiModel(value = "客户消息")
@AutoMapper(target = CustomerNotify.class)
public class TbCustomerNotify extends BaseTenantEntity {
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "消息类型")
    @Convert(converter = NotifyTypeEnum.Converter.class)
    private NotifyTypeEnum type;

    @ApiModelProperty(value = "实体id")
    private String entityId;

    @ApiModelProperty(value = "消息场景")
    @Convert(converter = NotifyScopeEnum.Converter.class)
    private NotifyScopeEnum scope;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "状态")
    @Convert(converter = MessageStateEnum.Converter.class)
    private MessageStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;
}