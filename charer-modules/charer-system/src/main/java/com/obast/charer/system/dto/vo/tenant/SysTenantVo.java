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

package com.obast.charer.system.dto.vo.tenant;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.system.SysTenant;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 租户视图对象 sys_tenant
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenant.class)
public class SysTenantVo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private String id;

    /**
     * 租户编号
     */
    @ExcelProperty(value = "租户编号")
    private String tenantId;

    /**
     * 联系人
     */
    @ExcelProperty(value = "联系人")
    private String contactUserName;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String contactPhone;

    /**
     * 企业名称
     */
    @ExcelProperty(value = "企业名称")
    private String companyName;

    /**
     * 统一社会信用代码
     */
    @ExcelProperty(value = "统一社会信用代码")
    private String licenseNumber;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址")
    private String address;

    /**
     * 域名
     */
    @ExcelProperty(value = "域名")
    private String domain;

    /**
     * 企业简介
     */
    @ExcelProperty(value = "企业简介")
    private String intro;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String note;

    /**
     * 租户套餐编号
     */
    @ExcelProperty(value = "租户套餐编号")
    private String packageId;

    /**
     * 过期时间
     */
    @ExcelProperty(value = "过期时间")
    private Date expireTime;

    /**
     * 用户数量（-1不限制）
     */
    @ExcelProperty(value = "用户数量")
    private Long accountCount;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;

    private String avatar;

    @ApiModelProperty(value = "平台显示名称")
    private String displayName;


    @ApiModelProperty(value = "平台分成比例")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal platformProfitPercent;
}
