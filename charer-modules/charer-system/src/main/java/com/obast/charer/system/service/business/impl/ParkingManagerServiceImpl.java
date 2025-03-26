package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IParkingData;
import com.obast.charer.model.parking.Parking;
import com.obast.charer.qo.ParkingQueryBo;
import com.obast.charer.system.dto.bo.ParkingBo;
import com.obast.charer.system.dto.vo.parking.ParkingVo;
import com.obast.charer.system.service.business.IParkingManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingManagerServiceImpl implements IParkingManagerService {

    @Autowired
    private IParkingData parkingData;

    @Override
    public Paging<ParkingVo> queryPageList(PageRequest<ParkingQueryBo> pageRequest) {
        Paging<Parking> pageList = parkingData.findPage(pageRequest);
        Paging<ParkingVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Parking parking: pageList.getRows()) {
            newPageList.getRows().add(fillData(parking));
        }
        return newPageList;
    }

    @Override
    public List<ParkingVo> queryList(PageRequest<ParkingQueryBo> pageRequest) {
        List<Parking> list = parkingData.findList(pageRequest.getData());
        List<ParkingVo> newList = new ArrayList<>();
        for(Parking parking: list) {
            newList.add(fillData(parking));
        }
        return newList;
    }


    @Override
    public ParkingVo queryDetail(String parkingId) {
        return fillData(parkingData.findById(parkingId));
    }

    private ParkingVo fillData(Parking parking) {
        return MapstructUtils.convert(parking, ParkingVo.class);
    }

    @Override
    public boolean add(ParkingBo bo) {
        if(bo == null) {
            throw new BizException(ErrCode.PARKING_EMPTY);
        }

        if(bo.getStationId() == null || bo.getStationId().isEmpty()) {
            throw new BizException(ErrCode.PARKING_STATION_EMPTY);
        }

        if(bo.getNo() == null || bo.getNo().isEmpty()) {
            throw new BizException(ErrCode.PARKING_NO_EMPTY);
        }

        //编号不能重复
        Parking repetition = parkingData.findByStationIdAndNo(bo.getStationId(), bo.getNo());
        if (repetition != null) {
            throw new BizException(ErrCode.PARKING_NO_ALREADY);
        }

        Parking charger = bo.to(Parking.class);

        parkingData.add(charger);
        return true;
    }

    @Override
    public boolean update(ParkingBo bo) {

        if(bo == null) {
            throw new BizException(ErrCode.PARKING_EMPTY);
        }

        if(bo.getStationId() == null || bo.getStationId().isEmpty()) {
            throw new BizException(ErrCode.PARKING_STATION_EMPTY);
        }

        if(bo.getNo() == null || bo.getNo().isEmpty()) {
            throw new BizException(ErrCode.PARKING_NO_EMPTY);
        }

        Parking di = bo.to(Parking.class);

        //编号不能重复
        Parking repetition = parkingData.findByStationIdAndNo(bo.getStationId(), bo.getNo());
        if (repetition != null && !repetition.getId().equals(di.getId())) {
            throw new BizException(ErrCode.PARKING_NO_ALREADY);
        }
        return parkingData.save(di) != null;
    }

    @Override
    public boolean delete(String parkingId) {
        parkingId = queryDetail(parkingId).getId();
        parkingData.deleteById(parkingId);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String parkingId: ids) {
            parkingData.deleteById(parkingId);
        }
        return true;
    }
}
