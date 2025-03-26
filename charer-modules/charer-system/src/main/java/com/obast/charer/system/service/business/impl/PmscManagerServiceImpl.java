package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.system.dto.bo.PmscBo;
import com.obast.charer.system.dto.vo.device.PmscVo;
import com.obast.charer.temporal.IThingModelMessageData;
import com.obast.charer.data.business.IPmscData;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.system.service.business.IPmscManagerService;
import com.obast.charer.model.device.Pmsc;
import com.obast.charer.qo.PmscQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;
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
public class PmscManagerServiceImpl implements IPmscManagerService {

    @Autowired
    private IPmscData PmscData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IPriceData priceData;

    @Lazy
    @Autowired
    private IThingModelMessageData thingModelMessageData;

    @Override
    public Paging<PmscVo> queryPageList(PageRequest<PmscQueryBo> pageRequest) {
        Paging<Pmsc> pageList = PmscData.findPage(pageRequest);
        Paging<PmscVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Pmsc Pmsc: pageList.getRows()) {
            newPageList.getRows().add(fillData(Pmsc));
        }
        return newPageList;
    }

    @Override
    public List<PmscVo> queryList(PageRequest<PmscQueryBo> pageRequest) {
        List<Pmsc> list = PmscData.findList(pageRequest.getData());
        List<PmscVo> PmscVos = new ArrayList<>();
        for(Pmsc Pmsc: list) {
            PmscVos.add(fillData(Pmsc));
        }
        return PmscVos;
    }

    @Override
    public PmscVo queryDetail(String PmscId) {
        Pmsc Pmsc = PmscData.findById(PmscId);
        return fillData(Pmsc);
    }

    private PmscVo fillData(Pmsc Pmsc) {
        PmscVo vo = MapstructUtils.convert(Pmsc, PmscVo.class);
        if(vo == null) {
            return null;
        }

        vo.setStation(stationData.findById(vo.getStationId()));

        return vo;
    }

    @Override
    @Transactional
    public void addPmsc(PmscBo bo) {
        //同产品不可重复设备名
        Pmsc PmscRepetition = PmscData.findByDn(bo.getName());
        if (PmscRepetition != null) {
            throw new BizException(ErrCode.DEVICE_ALREADY);
        }

        Pmsc Pmsc = bo.to(Pmsc.class);

        PmscData.add(Pmsc);
    }

    @Override
    public void updatePmsc(PmscBo bo) {
        Pmsc di = bo.to(Pmsc.class);
        PmscData.update(di);
    }

    @Override
    public void updateStatus(PmscBo bo) {
        Pmsc Pmsc = PmscData.findById(bo.getId());
        Pmsc.setStatus(bo.getStatus());
        PmscData.save(Pmsc);
    }

    @Override
    public void bindStation(PmscBo bo) {
        Pmsc Pmsc = PmscData.findById(bo.getId());
        Pmsc.setStationId(bo.getStationId());
        Pmsc.setDirect(bo.getDirect());
        PmscData.save(Pmsc);
    }

    @Override
    @Transactional
    public boolean deletePmsc(String PmscId) {
        Pmsc Pmsc = PmscData.findById(PmscId);
        if(Pmsc == null) {
            throw new BizException(ErrCode.DEVICE_NOT_FOUND);
        }
        PmscData.deleteById(PmscId);
        return true;
    }

    @Override
    public boolean batchDeletePmsc(List<String> ids) {
        for(String PmscId: ids) {
            PmscData.deleteById(PmscId);
        }
        return true;
    }

    @Override
    public Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request) {
        return thingModelMessageData.findPage(request);
    }
}
