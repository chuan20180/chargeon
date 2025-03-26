package com.obast.charer.system.service.business;

import com.obast.charer.system.dto.bo.RechargeBo;
import com.obast.charer.system.dto.vo.recharge.RechargeVo;
import com.obast.charer.qo.RechargeQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;


import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案服务接口
 */
public interface IRechargeManagerService {
    Paging<RechargeVo> queryPageList(PageRequest<RechargeQueryBo> pageRequest);

    List<RechargeVo> queryList(PageRequest<RechargeQueryBo> pageRequest);

    RechargeVo queryDetail(String rechargeId);

    boolean addRecharge(RechargeBo data);

    boolean updateRecharge(RechargeBo data);

    boolean updateStatus(RechargeBo bo);

    boolean removeRecharge(String id);

    boolean batchRemoveRecharge(List<String> ids);


}
