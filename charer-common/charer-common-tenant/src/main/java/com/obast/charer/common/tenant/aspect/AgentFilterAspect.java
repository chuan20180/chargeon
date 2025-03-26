package com.obast.charer.common.tenant.aspect;

import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.tenant.helper.AgentHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(2)
@Aspect
@Component
public class AgentFilterAspect {

    @Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
    public void openSession() {
    }

    @AfterReturning(pointcut = "openSession()", returning = "session")
    public void afterOpenSession(Object session) {

        //System.out.println("拦截到代理");

        if(!AgentHelper.isEnable() || AgentHelper.isIgnore()){
            return;
        }

        if (session instanceof Session) {
            if(LoginHelper.isTenantAdmin()) {
                return;
            }
            String agentId = LoginHelper.getAgentId();
            if (agentId != null) {
                org.hibernate.Filter filter = ((Session) session).enableFilter("agentFilter");
                filter.setParameter("agentId", agentId);
            }
        }
    }
}
