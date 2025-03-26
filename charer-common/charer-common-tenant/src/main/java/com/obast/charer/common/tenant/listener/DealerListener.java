package com.obast.charer.common.tenant.listener;

import com.obast.charer.common.satoken.util.LoginHelper;

import com.obast.charer.common.tenant.dao.DealerAware;


import com.obast.charer.common.tenant.helper.DealerHelper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

@Slf4j
public class DealerListener {

    //@PreUpdate
    @PreRemove
    @PrePersist
    public void setDealer(Object entity) {
        if(DealerHelper.isIgnore()){
            return;
        }
        String dealerId = LoginHelper.getDealerId();

        if ( dealerId != null) {
            DealerAware dealerAware = (DealerAware) entity;
            dealerAware.setDealerId(dealerId);
        }
    }
}
