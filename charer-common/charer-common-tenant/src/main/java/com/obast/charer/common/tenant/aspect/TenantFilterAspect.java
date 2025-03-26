package com.obast.charer.common.tenant.aspect;

import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.tenant.helper.TenantHelper;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Aspect
@Component
public class TenantFilterAspect {

    @Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
    public void openSession() {
    }

    @AfterReturning(pointcut = "openSession()", returning = "session")
    public void afterOpenSession(Object session) {

        //System.out.println("拦截到租户");

        if(!TenantHelper.isEnable() || TenantHelper.isIgnore()){
            return;
        }

        if (session instanceof Session) {
            String tenantId = LoginHelper.getTenantId();
            String dynamic = TenantHelper.getDynamic();
            if (ObjectUtil.isNotNull(dynamic)) {
                tenantId = dynamic;
            }


            if(LoginHelper.isSuperAdmin()) {
                return;
            }

           // System.out.println("拦截到租户2" + tenantId);


            if (tenantId != null) {
                org.hibernate.Filter filter = ((Session) session).enableFilter("tenantFilter");
                filter.setParameter("tenantId", tenantId);
            }
        }
    }
}
