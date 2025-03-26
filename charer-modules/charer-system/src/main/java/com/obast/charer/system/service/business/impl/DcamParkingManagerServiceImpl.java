package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.IDcamParkingData;
import com.obast.charer.system.dto.bo.DcamParkingBo;
import com.obast.charer.system.dto.vo.device.DcamParkingVo;
import com.obast.charer.system.service.business.IDcamParkingManagerService;
import com.obast.charer.model.device.DcamParking;
import com.obast.charer.qo.DcamParkingQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DcamParkingManagerServiceImpl implements IDcamParkingManagerService {

    @Autowired
    private IDcamParkingData dcamParkingData;

    @Override
    public Paging<DcamParkingVo> queryPageList(PageRequest<DcamParkingQueryBo> pageRequest) {
        Paging<DcamParking> pageList = dcamParkingData.findPage(pageRequest);
        Paging<DcamParkingVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(DcamParking dcamParking: pageList.getRows()) {
            newPageList.getRows().add(fillData(dcamParking));
        }
        return newPageList;
    }

    @Override
    public List<DcamParkingVo> queryList(PageRequest<DcamParkingQueryBo> pageRequest) {
        List<DcamParking> list = dcamParkingData.findList(pageRequest.getData());
        List<DcamParkingVo> newList = new ArrayList<>();
        for(DcamParking dcamParking: list) {
            newList.add(fillData(dcamParking));
        }
        return newList;
    }

    @Override
    public DcamParkingVo queryDetail(String parkingId) {
        return fillData(dcamParkingData.findById(parkingId));
    }

    private DcamParkingVo fillData(DcamParking dcamParking) {
        return MapstructUtils.convert(dcamParking, DcamParkingVo.class);
    }

    @Override
    public boolean add(DcamParkingBo bo) {
        DcamParking di = bo.to(DcamParking.class);

        if(StringUtils.isBlank(bo.getParkingId())) {
            throw new BizException(ErrCode.DCAM_PARKING_PARKING_ID_EMPTY);
        }

        if(StringUtils.isBlank(bo.getDcamId())) {
            throw new BizException(ErrCode.DCAM_PARKING_DCAM_ID_EMPTY);
        }

        if(StringUtils.isBlank(bo.getName())) {
            throw new BizException(ErrCode.DCAM_PARKING_NAME_EMPTY);
        }

        //不可重复设备名
        DcamParking repetition = dcamParkingData.findByDcamIdAndName(bo.getDcamId(), bo.getName());
        if (repetition != null) {
            throw new BizException(ErrCode.DCAM_PARKING_NAME_ALREADY);
        }

        return dcamParkingData.add(di) != null;
    }

    @Override
    public boolean update(DcamParkingBo bo) {
        DcamParking di = bo.to(DcamParking.class);
        return dcamParkingData.update(di) != null;
    }

    @Override
    public boolean delete(String id) {
        id = queryDetail(id).getId();
        dcamParkingData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String parkingId: ids) {
            dcamParkingData.deleteById(parkingId);
        }
        return true;
    }
}
