package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.system.dto.bo.DcamBo;
import com.obast.charer.system.dto.vo.device.DcamVo;
import com.obast.charer.system.service.business.IDcamManagerService;

import com.obast.charer.model.device.Dcam;

import com.obast.charer.qo.DcamQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;
import com.obast.charer.temporal.IThingModelMessageData;
import com.obast.charer.data.business.IDcamData;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.data.business.IStationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public class DcamManagerServiceImpl implements IDcamManagerService {

    @Autowired
    private IDcamData DcamData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IPriceData priceData;

    @Lazy
    @Autowired
    private IThingModelMessageData thingModelMessageData;

    @Override
    public Paging<DcamVo> queryPageList(PageRequest<DcamQueryBo> pageRequest) {
        Paging<Dcam> pageList = DcamData.findPage(pageRequest);
        Paging<DcamVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Dcam Dcam: pageList.getRows()) {
            newPageList.getRows().add(fillData(Dcam));
        }
        return newPageList;
    }

    @Override
    public List<DcamVo> queryList(PageRequest<DcamQueryBo> pageRequest) {
        List<Dcam> list = DcamData.findList(pageRequest.getData());
        List<DcamVo> DcamVos = new ArrayList<>();
        for(Dcam Dcam: list) {
            DcamVos.add(fillData(Dcam));
        }
        return DcamVos;
    }

    @Override
    public DcamVo queryDetail(String DcamId) {
        Dcam Dcam = DcamData.findById(DcamId);
        return fillData(Dcam);
    }

    private DcamVo fillData(Dcam Dcam) {
        DcamVo vo = MapstructUtils.convert(Dcam, DcamVo.class);
        if(vo == null) {
            return null;
        }

        vo.setStation(stationData.findById(vo.getStationId()));

        return vo;
    }

    @Override
    @Transactional
    public void addDcam(DcamBo bo) {
        //同产品不可重复设备名
        Dcam DcamRepetition = DcamData.findByDn(bo.getName());
        if (DcamRepetition != null) {
            throw new BizException(ErrCode.DEVICE_ALREADY);
        }

        Dcam Dcam = bo.to(Dcam.class);

        DcamData.add(Dcam);
    }

    @Override
    public void updateDcam(DcamBo bo) {
        Dcam di = bo.to(Dcam.class);
        DcamData.update(di);
    }

    @Override
    public void updateStatus(DcamBo bo) {
        Dcam Dcam = DcamData.findById(bo.getId());
        Dcam.setStatus(bo.getStatus());
        DcamData.save(Dcam);
    }

    @Override
    @Transactional
    public boolean deleteDcam(String DcamId) {
        Dcam Dcam = DcamData.findById(DcamId);
        if(Dcam == null) {
            throw new BizException(ErrCode.DEVICE_NOT_FOUND);
        }
        DcamData.deleteById(DcamId);
        return true;
    }

    @Override
    public boolean batchDeleteDcam(List<String> ids) {
        for(String DcamId: ids) {
            DcamData.deleteById(DcamId);
        }
        return true;
    }

    @Override
    public Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request) {
        return thingModelMessageData.findPage(request);
    }
}
