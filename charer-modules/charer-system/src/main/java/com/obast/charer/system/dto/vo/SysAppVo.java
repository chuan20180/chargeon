package com.obast.charer.system.dto.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.obast.charer.enums.AppTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.system.SysApp;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysApp.class)
public class SysAppVo extends BaseModel implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;

    /**
     * appId
     */
    @ApiModelProperty(value = "appId")
    private String appId;

    /**
     * appSecret
     */
    @ApiModelProperty(value = "appSecret")
    private String appSecret;

    /**
     * 属性
     */
    @ApiModelProperty(value = "属性")
    private String config;

    /**
     * 应用类型
     */
    @ApiModelProperty(value = "应用类型")
    private AppTypeEnum appType;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;
}
