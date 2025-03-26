package com.obast.charer.data.base;

import com.obast.charer.common.tenant.dao.AgentAware;
import com.obast.charer.common.tenant.listener.AgentListener;
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
@FilterDef(name = "agentFilter", parameters = {@ParamDef(name = "agentId", type = "string")})
@Filter(name = "agentFilter", condition = "agent_id = :agentId")
@EntityListeners({AgentListener.class})
public abstract class BaseAgentEntity extends BaseTenantEntity implements AgentAware {
    private static final long serialVersionUID = 2L;

    @Column(name = "agent_id")
    private String agentId;
}
