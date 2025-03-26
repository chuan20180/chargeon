/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.obast.charer.openapi.dto.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "TokenVerifyBo")
@Data
public class MobileInfoBo implements Serializable {
    private static final long serialVersionUID = -1L;

    @NotBlank(message = "手机号码不能为空")
    @ApiModelProperty(value = "mobile")
    private String mobile;

    @NotBlank(message = "消息ID不能为空")
    @ApiModelProperty(value = "messageId")
    private String messageId;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "code")
    private Integer code;

    @NotBlank(message = "用户信息不能为空")
    @ApiModelProperty(value = "code")
    private String nickName;

    @NotBlank(message = "dn不能为空")
    @ApiModelProperty(value = "code")
    private String dn;

}
