package com.obast.charer.openapi.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.openapi.dto.vo.OpenAdsVo;
import com.obast.charer.openapi.dto.vo.OpenPromotionVo;
import com.obast.charer.qo.AdsQueryBo;
import com.obast.charer.qo.PromotionQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 */
public interface IOpenPromotionService {
    Paging<OpenPromotionVo> queryPage(PageRequest<PromotionQueryBo> request);

    List<OpenPromotionVo> queryList(PromotionQueryBo bo);

    List<OpenPromotionVo> queryAvailableListById(String id);

    OpenPromotionVo queryDetail(String id);

}
