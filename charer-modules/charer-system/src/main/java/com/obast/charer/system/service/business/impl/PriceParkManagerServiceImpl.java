package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IDcamData;
import com.obast.charer.data.business.IPriceParkData;
import com.obast.charer.system.dto.bo.PriceParkBo;
import com.obast.charer.system.dto.vo.price.PriceParkVo;
import com.obast.charer.system.service.business.IPriceParkManagerService;
import com.obast.charer.model.device.Dcam;
import com.obast.charer.model.price.PricePark;
import com.obast.charer.qo.PriceParkQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：占位计费规则管理服务实现
 */
@Service
public class PriceParkManagerServiceImpl implements IPriceParkManagerService {

    @Autowired
    private IPriceParkData priceParkData;

    @Autowired
    private IDcamData dcamData;

    @Override
    public Paging<PriceParkVo> queryPageList(PageRequest<PriceParkQueryBo> pageRequest) {
        Paging<PricePark> pageList = priceParkData.findPage(pageRequest);
        Paging<PriceParkVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(PricePark pricePark: pageList.getRows()) {
            newPageList.getRows().add(fillData(pricePark));
        }
        return newPageList;
    }

    @Override
    public List<PriceParkVo> queryList(PageRequest<PriceParkQueryBo> pageRequest) {
        List<PricePark> list = priceParkData.findList(pageRequest.getData());
        List<PriceParkVo> newList = new ArrayList<>();
        for(PricePark pricePark: list) {
            newList.add(fillData(pricePark));
        }
        return newList;
    }

    @Override
    public PriceParkVo queryDetail(String priceParkId) {
        return fillData(priceParkData.findById(priceParkId));
    }

    private PriceParkVo fillData(PricePark pricePark) {
        return MapstructUtils.convert(pricePark, PriceParkVo.class);
    }

    @Override
    public boolean add(PriceParkBo bo) {
        PricePark entity = bo.to(PricePark.class);
        return priceParkData.add(entity) != null;
    }

    @Override
    public boolean update(PriceParkBo bo) {
        PricePark di = bo.to(PricePark.class);
        return priceParkData.update(di) != null;
    }

    @Override
    public void updateStatus(PriceParkBo bo) {
        PricePark price = priceParkData.findById(bo.getId());
        price.setStatus(bo.getStatus());
        priceParkData.save(price);
    }

    @Override
    public boolean delete(String priceParkId) {
        priceParkId = queryDetail(priceParkId).getId();

        //查看是否存在设备
        List<Dcam> dcams = dcamData.findByPriceParkId(priceParkId);
        if (!dcams.isEmpty()) {
            throw new BizException(ErrCode.PRICE_PARK_EXISTS_DCAM);
        }

        priceParkData.deleteById(priceParkId);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String priceId: ids) {
            //查看是否存在设备
            List<Dcam> dcams = dcamData.findByPriceParkId(priceId);
            if (!dcams.isEmpty()) {
                throw new BizException(ErrCode.PRICE_PARK_EXISTS_DCAM);
            }
            priceParkData.deleteById(priceId);
        }
        return true;
    }
}