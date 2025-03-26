package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.vo.park.ParkVo;
import com.obast.charer.qo.ParkQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：充电订单管理服务接口
 */
public interface IParkManagerService {
    Paging<ParkVo> queryPageList(PageRequest<ParkQueryBo> pageRequest);

    List<ParkVo> queryList(PageRequest<ParkQueryBo> pageRequest);

    ParkVo queryDetail(String chargerOrderId);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);

}
