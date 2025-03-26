package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.bo.PromotionBo;
import com.obast.charer.system.dto.vo.promotion.PromotionVo;
import com.obast.charer.qo.PromotionQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券服务接口
 */
public interface IPromotionManagerService {
    Paging<PromotionVo> queryPageList(PageRequest<PromotionQueryBo> pageRequest);

    List<PromotionVo> queryList(PageRequest<PromotionQueryBo> pageRequest);

    PromotionVo queryDetail(String stationId);

    boolean addPromotion(PromotionBo data);

    boolean updatePromotion(PromotionBo data);

    void updateStatus(PromotionBo bo);

    boolean removePromotion(String id);

    boolean batchRemovePromotion(List<String> ids);

    PromotionVo getDetail(String id);

}
