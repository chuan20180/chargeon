package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户信息业务对象 sys_user
 *
 * @author Michelle.Chung
 */

@Data
@NoArgsConstructor

@EqualsAndHashCode(callSuper = true)
public class UserOnlineBo extends BaseDto {


    /**
     * 用户账号
     */

    @ApiModelProperty(value = "用户账号")
    private String ipaddr;

    /**
     * 用户昵称
     */

    @ApiModelProperty("用户昵称")
    private String userName;


}
