package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.LedgerSettleBo;
import com.obast.charer.system.dto.vo.ledger.LedgerSettleVo;
import com.obast.charer.qo.LedgerSettleQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface ILedgerSettleManagerService {
    Paging<LedgerSettleVo> queryPageList(PageRequest<LedgerSettleQueryBo> pageRequest);

    List<LedgerSettleVo> queryList(PageRequest<LedgerSettleQueryBo> pageRequest);

    LedgerSettleVo queryDetail(String id);

    boolean addLedgerSettle(LedgerSettleBo data);

}
