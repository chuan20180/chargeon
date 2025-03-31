package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysOssConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 对象存储配置对象 sys_oss_config
 *
 * @author Lion Li
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_oss_config")
@ApiModel(value = "对象存储配置对象")
@AutoMapper(target = SysOssConfig.class)
public class TbSysOssConfig extends BaseEntity {

    /**
     * 主建
     */
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "主建")
    private String id;

    /**
     * 配置key
     */
    @ApiModelProperty(value = "配置key")
    private String configKey;

    /**
     * accessKey
     */
    @ApiModelProperty(value = "accessKey")
    private String accessKey;

    /**
     * 秘钥
     */
    @ApiModelProperty(value = "秘钥")
    private String secretKey;

    /**
     * 桶名称
     */
    @ApiModelProperty(value = "桶名称")
    private String bucketName;

    /**
     * 前缀
     */
    @ApiModelProperty(value = "前缀")
    private String prefix;

    /**
     * 访问站点
     */
    @ApiModelProperty(value = "访问站点")
    private String endpoint;

    /**
     * 自定义域名
     */
    @ApiModelProperty(value = "自定义域名")
    private String domain;

    @ApiModelProperty(value = "是否https（0否 1是）")
    private String isHttps;

    /**
     * 域
     */
    @ApiModelProperty(value = "域")
    private String region;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    /**
     * 扩展字段
     */
    @ApiModelProperty(value = "扩展字段")
    private String ext1;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 桶权限类型(0private 1public 2custom)
     */
    @ApiModelProperty(value = "桶权限类型(0private 1public 2custom)")
    private String accessPolicy;
}
