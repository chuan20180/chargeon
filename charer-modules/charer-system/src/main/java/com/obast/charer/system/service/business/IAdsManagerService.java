package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.bo.AdsBo;
import com.obast.charer.system.dto.vo.ads.AdsVo;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.qo.AdsQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：广告管理服务接口
 */
public interface IAdsManagerService {
    Paging<AdsVo> queryPageList(PageRequest<AdsQueryBo> pageRequest);

    List<AdsVo> queryList(PageRequest<AdsQueryBo> pageRequest);

    Ads queryDetail(String stationId);

    boolean addAds(AdsBo data);

    boolean updateAds(AdsBo data);

    boolean updateStatus(AdsBo bo);

    boolean deleteAds(String id);

    boolean batchDeleteAds(List<String> ids);


}
