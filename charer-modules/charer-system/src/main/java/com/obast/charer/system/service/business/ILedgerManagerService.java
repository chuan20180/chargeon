package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.vo.ledger.LedgerVo;
import com.obast.charer.model.ledger.Ledger;
import com.obast.charer.qo.LedgerQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface ILedgerManagerService {
    Paging<LedgerVo> queryPageList(PageRequest<LedgerQueryBo> pageRequest);

    List<LedgerVo> queryList(PageRequest<LedgerQueryBo> pageRequest);

    Ledger queryDetail(String chargerOrderId);
//
//    void reSettle();


}
