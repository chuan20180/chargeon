package com.obast.charer.system.dto.vo.customer;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.MessageStateEnum;
import com.obast.charer.enums.NotifyScopeEnum;
import com.obast.charer.enums.NotifyTypeEnum;
import com.obast.charer.model.customer.CustomerNotify;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomerNotifyVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CustomerNotify.class,convertGenerate = false)
public class CustomerNotifyVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "客户id")
    private String customerId;

    @ApiModelProperty(value = "消息类型")
    private NotifyTypeEnum type;

    @ApiModelProperty(value = "实体id")
    private String entityId;

    @ApiModelProperty(value = "消息场景")
    private NotifyScopeEnum scope;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "状态")
    private MessageStateEnum state;

    @ApiModelProperty(value = "描述")
    private String note;
}