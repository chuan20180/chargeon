package com.obast.charer.openapi.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.openapi.dto.bo.BalanceStartChargeBo;
import com.obast.charer.openapi.dto.bo.InstantStartChargeBo;
import com.obast.charer.openapi.dto.bo.StopChargeBo;
import com.obast.charer.openapi.dto.vo.OpenChargerGunVo;
import com.obast.charer.qo.ChargerGunQueryBo;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenChargerGunService {
    Paging<OpenChargerGunVo> queryPage(PageRequest<ChargerGunQueryBo> bo);

    OpenChargerGunVo queryDetailByChargerIdAndNo(String chargerId, String no);

    OpenChargerGunVo queryDetail(String stationId);

    ActionResult<?> startCharge(BalanceStartChargeBo bo);



    ActionResult<?> stopCharge(StopChargeBo bo);

}
