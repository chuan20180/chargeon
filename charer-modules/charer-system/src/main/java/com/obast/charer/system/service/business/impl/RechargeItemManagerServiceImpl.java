package com.obast.charer.system.service.business.impl;

import com.obast.charer.system.dto.bo.RechargeItemBo;
import com.obast.charer.system.dto.vo.recharge.RechargeItemVo;
import com.obast.charer.qo.RechargeItemQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.platform.IRechargeItemData;

import com.obast.charer.model.platform.RechargeItem;
import com.obast.charer.system.service.business.IRechargeItemManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案服务实现
 */
@Service
public class RechargeItemManagerServiceImpl implements IRechargeItemManagerService {

    @Autowired
    private IRechargeItemData rechargeItemData;


    @Override
    public Paging<RechargeItemVo> queryPageList(PageRequest<RechargeItemQueryBo> pageRequest) {
        Paging<RechargeItem> pageList = rechargeItemData.findPage(pageRequest);
        Paging<RechargeItemVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(RechargeItem recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public List<RechargeItemVo> queryList(PageRequest<RechargeItemQueryBo> pageRequest) {
        List<RechargeItem> list = rechargeItemData.findList(pageRequest.getData());
        List<RechargeItemVo> rechargeItemVos = new ArrayList<>();
        for(RechargeItem rechargeItem: list) {
            rechargeItemVos.add(fillData(rechargeItem));
        }
        return rechargeItemVos;
    }

    @Override
    public RechargeItemVo queryDetail(String id) {
        RechargeItem rechargeItem = rechargeItemData.findById(id);
        return fillData(rechargeItem);
    }

    private RechargeItemVo fillData(RechargeItem rechargeItem) {
        return MapstructUtils.convert(rechargeItem, RechargeItemVo.class);
    }

    @Override
    public boolean addRechargeItem(RechargeItemBo bo) {
        RechargeItem entity = bo.to(RechargeItem.class);
        return rechargeItemData.save(entity) != null;
    }

    @Override
    public boolean updateRechargeItem(RechargeItemBo bo) {
        RechargeItem di = bo.to(RechargeItem.class);
        return rechargeItemData.save(di) != null;
    }

    @Override
    public boolean updateStatus(RechargeItemBo bo) {
        RechargeItem recharge = rechargeItemData.findById(bo.getId());
        recharge.setStatus(bo.getStatus());
        rechargeItemData.save(recharge);
        return true;
    }

    @Override
    public boolean removeRechargeItem(String id) {
        id = queryDetail(id).getId();
        rechargeItemData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchRemoveRechargeItem(List<String> ids) {
        rechargeItemData.deleteByIds(ids);
        return true;
    }
}