package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.system.dto.bo.DcamBo;
import com.obast.charer.system.dto.vo.device.DcamVo;
import com.obast.charer.qo.DcamQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务接口
 */
public interface IDcamManagerService {
    Paging<DcamVo> queryPageList(PageRequest<DcamQueryBo> pageRequest);

    List<DcamVo> queryList(PageRequest<DcamQueryBo> pageRequest);

    DcamVo queryDetail(String DcamId);

    void addDcam(DcamBo data);

    void updateDcam(DcamBo data);

    void updateStatus(DcamBo bo);

    boolean deleteDcam(String id);

    boolean batchDeleteDcam(List<String> ids);


    Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request);

}
