package com.obast.charer.data.base;

import com.obast.charer.common.tenant.dao.TenantAware;
import com.obast.charer.common.tenant.listener.TenantListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
@EntityListeners(TenantListener.class)
public abstract class BaseTenantEntity extends BaseEntity implements TenantAware {
    private static final long serialVersionUID = 1L;

    @Column(name = "tenant_id")
    private String tenantId;


}
