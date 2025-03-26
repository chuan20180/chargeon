package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.DateUtils;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IParkData;
import com.obast.charer.system.dto.vo.park.ParkVo;
import com.obast.charer.system.service.business.IParkManagerService;
import com.obast.charer.model.park.Park;
import com.obast.charer.qo.ParkQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Service
public class ParkManagerServiceImpl implements IParkManagerService {

    @Autowired
    private IParkData parkData;


    @Override
    public Paging<ParkVo> queryPageList(PageRequest<ParkQueryBo> pageRequest) {
        Paging<Park> pageList = parkData.findPage(pageRequest);
        Paging<ParkVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Park park: pageList.getRows()) {
            newPageList.getRows().add(fillData(park));
        }
        return newPageList;
    }

    @Override
    public List<ParkVo> queryList(PageRequest<ParkQueryBo> pageRequest) {
        List<Park> list = parkData.findList(pageRequest.getData());
        List<ParkVo> newList = new ArrayList<>();
        for(Park park: list) {
            newList.add(fillData(park));
        }
        return newList;
    }

    @Override
    public ParkVo queryDetail(String id) {
        return fillData(parkData.findById(id));
    }

    @Override
    public boolean delete(String id) {
        id = queryDetail(id).getId();
        parkData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String id: ids) {
            parkData.deleteById(id);
        }
        return true;
    }

    private ParkVo fillData(Park park) {
        ParkVo vo = MapstructUtils.convert(park, ParkVo.class);
        if(vo == null) {
            return null;
        }

        Date inTime = park.getInTime();

        Date outTime = new Date();
        if(park.getOutTime()!= null) {
            outTime = park.getOutTime();
        }
        vo.setOccupyMinute(TimeUnit.MILLISECONDS.toMinutes(outTime.getTime() - inTime.getTime()));
        vo.setOccupyText(DateUtils.getDatePoor(outTime, inTime));
        return vo;
    }
}
