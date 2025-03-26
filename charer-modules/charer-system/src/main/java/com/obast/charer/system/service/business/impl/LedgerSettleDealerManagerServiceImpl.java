package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ILedgerSettleDealerData;
import com.obast.charer.system.dto.vo.ledger.LedgerSettleDealerVo;
import com.obast.charer.system.service.business.ILedgerSettleDealerManagerService;
import com.obast.charer.enums.LedgerSettleStateEnum;
import com.obast.charer.model.ledger.LedgerSettleDealer;
import com.obast.charer.qo.LedgerSettleDealerQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：分成结算列表服务实现
 */
@Service
public class LedgerSettleDealerManagerServiceImpl implements ILedgerSettleDealerManagerService {

    @Autowired
    private ILedgerSettleDealerData ledgerSettleDealerData;

    @Override
    public Paging<LedgerSettleDealerVo> queryPageList(PageRequest<LedgerSettleDealerQueryBo> pageRequest) {
        Paging<LedgerSettleDealer> pageList = ledgerSettleDealerData.findPage(pageRequest);
        Paging<LedgerSettleDealerVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(LedgerSettleDealer station: pageList.getRows()) {
            newPageList.getRows().add(fillData(station));
        }
        return newPageList;
    }

    @Override
    public List<LedgerSettleDealerVo> queryList(PageRequest<LedgerSettleDealerQueryBo> pageRequest) {
        List<LedgerSettleDealer> list = ledgerSettleDealerData.findList(pageRequest.getData());
        List<LedgerSettleDealerVo> newList = new ArrayList<>();
        for(LedgerSettleDealer station: list) {
            newList.add(fillData(station));
        }
        return newList;
    }

    @Override
    public LedgerSettleDealerVo queryDetail(String stationId) {
        return fillData(ledgerSettleDealerData.findById(stationId));
    }

    @Override
    @Transactional
    public void paid(String ledgerSettleDealerId, String note) {
        LedgerSettleDealer ledgerSettleDealer = ledgerSettleDealerData.findById(ledgerSettleDealerId);
        if(ledgerSettleDealer == null) {
            throw new BizException(ErrCode.LEDGER_SETTLE_DEALER_NOT_FOUND);
        }

        if(ledgerSettleDealer.getState() == null || ledgerSettleDealer.getState().equals(LedgerSettleStateEnum.Paid)) {
            throw new BizException(ErrCode.LEDGER_SETTLE_DEALER_STATE_ERROR);
        }

        ledgerSettleDealer.setState(LedgerSettleStateEnum.Paid);
        ledgerSettleDealer.setPaidTime(new Date());
        ledgerSettleDealerData.save(ledgerSettleDealer);
    }

    private LedgerSettleDealerVo fillData(LedgerSettleDealer station) {
        return MapstructUtils.convert(station, LedgerSettleDealerVo.class);
    }
}