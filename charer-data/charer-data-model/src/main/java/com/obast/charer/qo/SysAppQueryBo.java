package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysAppQueryBo extends BaseDto {

    /**
     * id
     */
    private String id;

    private String tenantId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * appId
     */
    private String appId;


    /**
     * 应用类型
     */
    private String appType;



}
