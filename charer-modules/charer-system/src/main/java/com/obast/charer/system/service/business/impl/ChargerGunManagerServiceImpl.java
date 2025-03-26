package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.qo.ChargerGunQueryBo;
import com.obast.charer.system.dto.bo.ChargerGunBo;
import com.obast.charer.system.dto.vo.device.ChargerGunVo;
import com.obast.charer.system.service.business.IChargerGunManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务实现
 */
@Service
public class ChargerGunManagerServiceImpl implements IChargerGunManagerService {

    @Autowired
    private IChargerGunData chargerGunData;

    @Override
    public Paging<ChargerGunVo> queryPageList(PageRequest<ChargerGunQueryBo> pageRequest) {
        Paging<ChargerGun> pageList = chargerGunData.findPage(pageRequest);
        Paging<ChargerGunVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(ChargerGun chargerGun: pageList.getRows()) {
            newPageList.getRows().add(fillData(chargerGun));
        }
        return newPageList;
    }

    @Override
    public List<ChargerGunVo> queryList(PageRequest<ChargerGunQueryBo> pageRequest) {
        List<ChargerGun> list = chargerGunData.findList(pageRequest.getData());
        List<ChargerGunVo> chargerGunVos = new ArrayList<>();
        for(ChargerGun chargerGun: list) {
            chargerGunVos.add(fillData(chargerGun));
        }
        return chargerGunVos;
    }

    @Override
    public ChargerGunVo queryDetail(String id) {
        ChargerGun charger = chargerGunData.findById(id);
        return fillData(charger);
    }

    private ChargerGunVo fillData(ChargerGun chargerGun) {
        return MapstructUtils.convert(chargerGun, ChargerGunVo.class);
    }

    @Override
    @Transactional
    public void add(ChargerGunBo bo) {
        ChargerGun chargerGun = bo.to(ChargerGun.class);
        chargerGunData.add(chargerGun);
    }

    @Override
    public void update(ChargerGunBo bo) {
        ChargerGun di = bo.to(ChargerGun.class);
        chargerGunData.update(di);
    }

    @Override
    @Transactional
    public void bindParking(ChargerGunBo bo) {
        if(bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }

        ChargerGun chargerGun = chargerGunData.findById(bo.getId());
        if(chargerGun == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }
        if(StringUtils.isNoneBlank(bo.getParkingId())) {
            boolean checkParkingUnique = chargerGunData.checkParkingUnique(bo.to(ChargerGun.class));
            if(!checkParkingUnique) {
                throw new BizException(ErrCode.PARKING_AlREADY_BIND_TO_CHARGER_GUN);
            }
        }

        chargerGun.setParkingId(bo.getParkingId());
        chargerGunData.update(chargerGun);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        chargerGunData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String id: ids) {
            chargerGunData.deleteById(id);
        }
        return true;
    }
}
