package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.DcamParkingBo;
import com.obast.charer.system.dto.vo.device.DcamParkingVo;
import com.obast.charer.qo.DcamParkingQueryBo;

import java.util.List;

public interface IDcamParkingManagerService {

    Paging<DcamParkingVo> queryPageList(PageRequest<DcamParkingQueryBo> pageRequest);

    List<DcamParkingVo> queryList(PageRequest<DcamParkingQueryBo> pageRequest);

    DcamParkingVo queryDetail(String id);

    boolean add(DcamParkingBo data);

    boolean update(DcamParkingBo data);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);
}
