package com.obast.charer.system.dto.vo;

import com.obast.charer.model.system.SysOss;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * OSS对象存储视图对象 sys_oss
 *
 * @author Lion Li
 */
@Data
@AutoMapper(target = SysOss.class)
public class SysOssVo implements Serializable {
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 上传人
     */
    private Long createBy;

    /**
     * 上传人名称
     */
    private String createByName;

    /**
     * 服务商
     */
    private String service;


}
