package com.obast.charer.common.tenant.aspect;


import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.tenant.helper.DealerHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(3)
@Aspect
@Component
public class DealerFilterAspect {

    @Pointcut("execution (* org.hibernate.internal.SessionFactoryImpl.SessionBuilderImpl.openSession(..))")
    public void openSession() {
    }

    @AfterReturning(pointcut = "openSession()", returning = "session")
    public void afterOpenSession(Object session) {

        //System.out.println("拦截到合作商");

        if(!DealerHelper.isEnable() || DealerHelper.isIgnore()){
            return;
        }

        if (session instanceof Session) {
            if(LoginHelper.isTenantAdmin()) {
                return;
            }
            String dealerId = LoginHelper.getDealerId();
            //System.out.println("找到封校商" + JsonUtils.toJsonString(dealerId));
            if (dealerId != null) {
                org.hibernate.Filter filter = ((Session) session).enableFilter("dealerFilter");
                filter.setParameter("dealerId", dealerId);
            }
        }
    }
}
