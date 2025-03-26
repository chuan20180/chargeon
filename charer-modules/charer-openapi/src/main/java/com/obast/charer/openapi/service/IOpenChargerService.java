package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenChargerVo;
import com.obast.charer.qo.ChargerQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenChargerService {
    Paging<OpenChargerVo> queryPage(PageRequest<ChargerQueryBo> bo);

    OpenChargerVo queryDetail(String stationId);

    OpenChargerVo queryDetailByDn(String dn);

}
