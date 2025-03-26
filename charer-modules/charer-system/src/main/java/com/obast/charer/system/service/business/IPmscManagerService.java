package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.system.dto.bo.PmscBo;
import com.obast.charer.system.dto.vo.device.PmscVo;
import com.obast.charer.qo.PmscQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务接口
 */
public interface IPmscManagerService {
    Paging<PmscVo> queryPageList(PageRequest<PmscQueryBo> pageRequest);

    List<PmscVo> queryList(PageRequest<PmscQueryBo> pageRequest);

    PmscVo queryDetail(String PmscId);

    void addPmsc(PmscBo data);

    void updatePmsc(PmscBo data);

    void updateStatus(PmscBo bo);

    void bindStation(PmscBo bo);

    boolean deletePmsc(String id);

    boolean batchDeletePmsc(List<String> ids);


    Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request);

}
