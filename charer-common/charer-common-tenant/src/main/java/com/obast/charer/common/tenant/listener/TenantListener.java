package com.obast.charer.common.tenant.listener;

import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.tenant.dao.TenantAware;

import com.obast.charer.common.tenant.helper.TenantHelper;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.util.Objects;

@Slf4j
public class TenantListener {

    @PreUpdate
    @PreRemove
    @PrePersist
    public void setTenant(Object entity) {
        if(TenantHelper.isIgnore()){
            return;
        }
        String tenantId = LoginHelper.getTenantId();
        String dynamic = TenantHelper.getDynamic();
        if (!Objects.isNull(dynamic)) {
            tenantId = dynamic;
        }
        if ( tenantId != null) {
            TenantAware tenantAware = (TenantAware) entity;
            tenantAware.setTenantId(tenantId);
        }
    }
}
