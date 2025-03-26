package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IRechargeData;
import com.obast.charer.data.platform.IRechargeItemData;
import com.obast.charer.model.platform.Recharge;
import com.obast.charer.model.platform.RechargeItem;
import com.obast.charer.openapi.dto.vo.OpenRechargeItemVo;
import com.obast.charer.openapi.dto.vo.OpenRechargeVo;
import com.obast.charer.openapi.service.IOpenRechargeService;
import com.obast.charer.qo.RechargeQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenRechargeServiceImpl implements IOpenRechargeService {

    @Autowired
    private IRechargeData rechargeData;

    @Autowired
    private IRechargeItemData rechargeItemData;


    @Override
    public Paging<OpenRechargeVo> queryPage(PageRequest<RechargeQueryBo> pageRequest) {
        Paging<Recharge> pageList = rechargeData.findPage(pageRequest);
        Paging<OpenRechargeVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Recharge recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public List<OpenRechargeVo> queryList(RechargeQueryBo bo) {
        List<Recharge> list = rechargeData.findList(bo);
        List<OpenRechargeVo> newList = new ArrayList<>();
        for(Recharge recharge: list) {
            newList.add(fillData(recharge));
        }
        return newList;
    }

    @Override
    public OpenRechargeVo queryDetail(String stationId) {
        Recharge recharge = rechargeData.findById(stationId);
        return fillData(recharge);
    }

    private OpenRechargeVo fillData(Recharge recharge) {
        OpenRechargeVo vo = MapstructUtils.convert(recharge, OpenRechargeVo.class);
        if(vo == null) {
            return null;
        }

        List<RechargeItem> items = rechargeItemData.findByRechargeId(recharge.getId());
        vo.setItems(MapstructUtils.convert(items, OpenRechargeItemVo.class));

        return vo;
    }
}
