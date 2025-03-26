package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.model.PaymentNotify;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.qo.TopupQueryBo;
import com.obast.charer.system.dto.bo.TopupBo;
import com.obast.charer.system.dto.vo.topup.TopupVo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值记录服务接口
 */
public interface ITopupManagerService {
    Paging<TopupVo> queryPageList(PageRequest<TopupQueryBo> pageRequest);

    List<TopupVo> queryList(PageRequest<TopupQueryBo> pageRequest);

    TopupVo queryDetail(String topupId);

    boolean removeTopup(String id);

    boolean batchRemoveTopup(List<String> ids);

    void add(TopupBo bo);

    Topup getDetail(String id);

    void complete(PaymentNotify paymentNotify);



}
