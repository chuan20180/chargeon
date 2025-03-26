package com.obast.charer.model.system;

import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * OSS对象存储视图对象 sys_oss
 *
 * @author Lion Li
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysOss  extends TenantModel implements Id<String>,Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 对象存储主键
     */
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原名
     */
    private String originalName;

    /**
     * 文件后缀名
     */
    private String fileSuffix;

    /**
     * URL地址
     */
    private String url;

    /**
     * 服务商
     */
    private String service;


}
