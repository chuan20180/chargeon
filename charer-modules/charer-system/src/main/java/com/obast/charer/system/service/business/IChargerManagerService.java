package com.obast.charer.system.service.business;

import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.system.dto.bo.ChargerBo;
import com.obast.charer.system.dto.vo.device.ChargerVo;
import com.obast.charer.qo.DeviceLogQueryBo;
import com.obast.charer.qo.ChargerQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;


import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务接口
 */
public interface IChargerManagerService {
    Paging<ChargerVo> queryPageList(PageRequest<ChargerQueryBo> pageRequest);

    List<ChargerVo> queryList(PageRequest<ChargerQueryBo> pageRequest);

    ChargerVo queryDetail(String chargerId);

    void addCharger(ChargerBo data);

    void updateCharger(ChargerBo data);

    void updateStatus(ChargerBo bo);

    boolean deleteCharger(String id);

    boolean batchDeleteCharger(List<String> ids);


    Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request);

}
