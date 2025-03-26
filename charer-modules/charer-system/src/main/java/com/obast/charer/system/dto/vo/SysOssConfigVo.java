package com.obast.charer.system.dto.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.obast.charer.common.api.BaseTenantDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysOssConfig;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ Author：chuan
 * @ Date：2024-09-27-17:19
 * @ Version：1.0
 * @ Description：对象存储配置视图对象 sys_oss_config
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysOssConfig.class)
public class SysOssConfigVo extends BaseTenantDto {
    private static final long serialVersionUID = 1L;

    /**
     * 主建
     */
    private String id;

    /**
     * 配置key
     */
    private String configKey;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 访问站点
     */
    private String endpoint;

    /**
     * 自定义域名
     */
    private String domain;

    /**
     * 是否https（Y=是,N=否）
     */
    private String isHttps;

    /**
     * 域
     */
    private String region;

    /**
     * 是否默认（0=是,1=否）
     */
    private EnableStatusEnum status;

    /**
     * 扩展字段
     */
    private String ext1;

    /**
     * 备注
     */
    private String remark;

    /**
     * 桶权限类型(0private 1public 2custom)
     */
    private String accessPolicy;

}
