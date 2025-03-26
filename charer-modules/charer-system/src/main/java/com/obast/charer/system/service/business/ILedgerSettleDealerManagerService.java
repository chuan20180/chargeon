package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.vo.ledger.LedgerSettleDealerVo;
import com.obast.charer.qo.LedgerSettleDealerQueryBo;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站管理服务接口
 */
public interface ILedgerSettleDealerManagerService {
    Paging<LedgerSettleDealerVo> queryPageList(PageRequest<LedgerSettleDealerQueryBo> pageRequest);

    List<LedgerSettleDealerVo> queryList(PageRequest<LedgerSettleDealerQueryBo> pageRequest);

    LedgerSettleDealerVo queryDetail(String stationId);

    void paid(String ledgerSettleDealerId, String note);






}
