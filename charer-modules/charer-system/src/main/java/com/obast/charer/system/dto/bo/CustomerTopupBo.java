package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.model.customer.Customer;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel(value = "CustomerTopupBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Customer.class, reverseConvertGenerate = false)
public class CustomerTopupBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private String id;

    @NotNull(message = "客户id为空")
    private String customerId;

    @NotNull(message = "充值金额为空")
    private BigDecimal amount;

    private String note;


}
