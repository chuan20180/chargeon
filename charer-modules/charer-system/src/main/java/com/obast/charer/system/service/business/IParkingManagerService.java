package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.ParkingQueryBo;
import com.obast.charer.system.dto.bo.ParkingBo;
import com.obast.charer.system.dto.vo.parking.ParkingVo;

import java.util.List;

public interface IParkingManagerService {

    Paging<ParkingVo> queryPageList(PageRequest<ParkingQueryBo> pageRequest);

    List<ParkingVo> queryList(PageRequest<ParkingQueryBo> pageRequest);

    ParkingVo queryDetail(String parkingId);

    boolean add(ParkingBo data);

    boolean update(ParkingBo data);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);
}
