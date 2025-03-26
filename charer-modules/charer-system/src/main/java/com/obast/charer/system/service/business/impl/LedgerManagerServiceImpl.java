package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ILedgerData;
import com.obast.charer.data.business.ILedgerSettleData;
import com.obast.charer.data.business.IOrdersData;
import com.obast.charer.system.dto.vo.ledger.LedgerVo;
import com.obast.charer.system.service.business.ILedgerManagerService;

import com.obast.charer.model.ledger.Ledger;
import com.obast.charer.qo.LedgerQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Service
public class LedgerManagerServiceImpl implements ILedgerManagerService {

    @Autowired
    private ILedgerData ledgerData;

    @Autowired
    private ILedgerSettleData ledgerSettleData;

    @Autowired
    private IOrdersData ordersData;
    
    @Override
    public Paging<LedgerVo> queryPageList(PageRequest<LedgerQueryBo> pageRequest) {
        return ledgerData.findPage(pageRequest).to(LedgerVo.class);
    }

    @Override
    public List<LedgerVo> queryList(PageRequest<LedgerQueryBo> pageRequest) {
        return MapstructUtils.convert(ledgerData.findList(pageRequest.getData()), LedgerVo.class);
    }

    @Override
    public Ledger queryDetail(String chargerOrderId) {
        return ledgerData.findById(chargerOrderId);
    }

   /* @Override
    @Transactional
    public void reSettle() {
        OrdersQueryBo ordersQueryBo = new OrdersQueryBo();
        ordersQueryBo.setState(OrderStateEnum.Settled);
        List<Orders> orders = ordersData.findList(ordersQueryBo);
        if(!orders.isEmpty()) {
            for(Orders order: orders) {
                order.setDeal(OrderDealEnum.NoDeal);
                ordersData.save(order);
            }
        }

        List<Ledger> ledgers = ledgerData.findAll();
        if(!ledgers.isEmpty()) {
            for(Ledger ledger: ledgers) {
                ledgerData.deleteById(ledger.getId());
            }
        }

        List<LedgerSettle> ledgerSettles = ledgerSettleData.findAll();
        if(!ledgerSettles.isEmpty()) {
            for(LedgerSettle ledgerSettle: ledgerSettles) {
                ledgerSettleData.deleteById(ledgerSettle.getId());
            }
        }

        if(!orders.isEmpty()) {
            for(Orders order: orders) {
                chargerOrdersService.deal(order.getId());
            }
        }
    }*/
}
