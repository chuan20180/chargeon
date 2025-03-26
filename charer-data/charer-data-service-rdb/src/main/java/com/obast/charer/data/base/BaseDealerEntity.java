package com.obast.charer.data.base;

import com.obast.charer.common.tenant.dao.DealerAware;
import com.obast.charer.common.tenant.listener.DealerListener;
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
@FilterDef(name = "dealerFilter", parameters = {@ParamDef(name = "dealerId", type = "string")})
@Filter(name = "dealerFilter", condition = "dealer_id = :dealerId")
@EntityListeners({DealerListener.class, TenantListener.class})
public abstract class BaseDealerEntity extends BaseAgentEntity implements DealerAware {
    private static final long serialVersionUID = 2L;

    @Column(name = "dealer_id")
    private String dealerId;
}
