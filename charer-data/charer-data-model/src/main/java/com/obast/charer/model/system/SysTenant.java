package com.obast.charer.model.system;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
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
public class SysTenant extends BaseModel implements Id<String>,Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 租户编号
     */
    private String tenantId;

    /**
     * 联系人
     */
    private String contactUserName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 统一社会信用代码
     */
    private String licenseNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 域名
     */
    private String domain;

    /**
     * 企业简介
     */
    private String intro;

    /**
     * 备注
     */
    private String note;

    /**
     * 租户套餐编号
     */
    private String packageId;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 用户数量（-1不限制）
     */
    private Long accountCount;

    /**
     * 租户状态（1正常 0停用）
     */
    private EnableStatusEnum status;


    private BigDecimal platformProfitPercent;

    /**
     * 头像地址
     */
    private String avatar;


    private String displayName;

}
