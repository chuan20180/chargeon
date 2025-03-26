package com.obast.charer.system.service.business;

import com.obast.charer.system.dto.bo.RechargeItemBo;
import com.obast.charer.system.dto.vo.recharge.RechargeItemVo;
import com.obast.charer.qo.RechargeItemQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;


import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案服务接口
 */
public interface IRechargeItemManagerService {
    Paging<RechargeItemVo> queryPageList(PageRequest<RechargeItemQueryBo> pageRequest);

    List<RechargeItemVo> queryList(PageRequest<RechargeItemQueryBo> pageRequest);

    RechargeItemVo queryDetail(String id);

    boolean addRechargeItem(RechargeItemBo data);

    boolean updateRechargeItem(RechargeItemBo data);

    boolean updateStatus(RechargeItemBo bo);

    boolean removeRechargeItem(String id);

    boolean batchRemoveRechargeItem(List<String> ids);


}
