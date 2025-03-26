package com.obast.charer.common.tenant.listener;

import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.tenant.dao.AgentAware;


import com.obast.charer.common.tenant.helper.AgentHelper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

@Slf4j
public class AgentListener {

    @PreRemove
    @PrePersist
    public void setAgent(Object entity) {
        if(AgentHelper.isIgnore()){
            return;
        }
        String agentId = LoginHelper.getAgentId();

        if ( agentId != null) {
            AgentAware agentAware = (AgentAware) entity;
            agentAware.setAgentId(agentId);
        }
    }
}
