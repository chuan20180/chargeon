package com.obast.charer.system.service.business.impl;

import com.obast.charer.system.dto.bo.RechargeBo;
import com.obast.charer.system.dto.vo.recharge.RechargeItemVo;
import com.obast.charer.system.dto.vo.recharge.RechargeVo;
import com.obast.charer.qo.RechargeQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IRechargeData;
import com.obast.charer.data.platform.IRechargeItemData;

import com.obast.charer.model.platform.Recharge;
import com.obast.charer.model.platform.RechargeItem;
import com.obast.charer.system.service.business.IRechargeManagerService;
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
public class RechargeManagerServiceImpl implements IRechargeManagerService {

    @Autowired
    private IRechargeData rechargeData;

    @Autowired
    private IRechargeItemData rechargeItemData;

    @Override
    public Paging<RechargeVo> queryPageList(PageRequest<RechargeQueryBo> pageRequest) {
        Paging<Recharge> pageList = rechargeData.findPage(pageRequest);
        Paging<RechargeVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Recharge recharge: pageList.getRows()) {
            newPageList.getRows().add(fillData(recharge));
        }
        return newPageList;
    }

    @Override
    public List<RechargeVo> queryList(PageRequest<RechargeQueryBo> pageRequest) {
        List<Recharge> list = rechargeData.findList(pageRequest.getData());
        List<RechargeVo> chargerVos = new ArrayList<>();
        for(Recharge charger: list) {
            chargerVos.add(fillData(charger));
        }
        return chargerVos;
    }

    @Override
    public RechargeVo queryDetail(String rechargeId) {
        Recharge recharge = rechargeData.findById(rechargeId);
        return fillData(recharge);
    }

    private RechargeVo fillData(Recharge recharge) {
        RechargeVo vo = MapstructUtils.convert(recharge, RechargeVo.class);
        if(vo == null) {
            return null;
        }

        List<RechargeItem> rechargeItem = rechargeItemData.findByRechargeId(recharge.getId());
        vo.setItems(MapstructUtils.convert(rechargeItem, RechargeItemVo.class));
        return vo;
    }

    @Override
    public boolean addRecharge(RechargeBo bo) {
        Recharge entity = bo.to(Recharge.class);
        //名称不可重复
        Recharge repetition = rechargeData.findByName(entity.getName());
        if (repetition != null) {
            throw new BizException(ErrCode.RECHARGE_NAME_ALREADY);
        }
        return rechargeData.add(entity) != null;
    }

    @Override
    public boolean updateRecharge(RechargeBo bo) {
        Recharge di = bo.to(Recharge.class);
        //名称不可重复
        Recharge repetition = rechargeData.findByName(di.getName());
        if (repetition != null && !repetition.getId().equals(di.getId())) {
            throw new BizException(ErrCode.RECHARGE_NAME_ALREADY);
        }
        return rechargeData.update(di) != null;
    }

    @Override
    public boolean updateStatus(RechargeBo bo) {
        Recharge recharge = rechargeData.findById(bo.getId());
        recharge.setStatus(bo.getStatus());
        rechargeData.save(recharge);
        return true;
    }

    @Override
    public boolean removeRecharge(String priceId) {
        priceId = queryDetail(priceId).getId();
        rechargeData.deleteById(priceId);
        return true;
    }

    @Override
    public boolean batchRemoveRecharge(List<String> ids) {
        rechargeData.deleteByIds(ids);
        return true;
    }
}