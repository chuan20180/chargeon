package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IAdsData;
import com.obast.charer.system.dto.bo.AdsBo;
import com.obast.charer.system.dto.vo.ads.AdsVo;
import com.obast.charer.system.service.business.IAdsManagerService;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.qo.AdsQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：广告管理服务实现
 */
@Service
public class AdsManagerServiceImpl implements IAdsManagerService {

    @Autowired
    private IAdsData adsData;

    @Override
    public Paging<AdsVo> queryPageList(PageRequest<AdsQueryBo> pageRequest) {
        return MapstructUtils.convert(adsData.findPage(pageRequest), AdsVo.class);
    }

    @Override
    public List<AdsVo> queryList(PageRequest<AdsQueryBo> pageRequest) {
        return MapstructUtils.convert(adsData.findList(pageRequest.getData()), AdsVo.class);
    }

    @Override
    public Ads queryDetail(String stationId) {
        return adsData.findById(stationId);
    }

    @Override
    public boolean addAds(AdsBo bo) {
        Ads di = bo.to(Ads.class);
        return adsData.add(di) != null;
    }

    @Override
    public boolean updateAds(AdsBo bo) {
        Ads di = bo.to(Ads.class);
        return adsData.update(di) != null;
    }

    @Override
    public boolean updateStatus(AdsBo bo) {
        Ads ads = adsData.findById(bo.getId());
        ads.setStatus(bo.getStatus());
        adsData.save(ads);
        return true;
    }

    @Override
    public boolean deleteAds(String id) {
        adsData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDeleteAds(List<String> ids) {
        for(String stationId: ids) {
            adsData.deleteById(stationId);
        }
        return true;
    }
}