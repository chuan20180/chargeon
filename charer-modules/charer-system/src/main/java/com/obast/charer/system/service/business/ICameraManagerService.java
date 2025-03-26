package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.system.dto.bo.CameraBo;
import com.obast.charer.system.dto.vo.device.CameraVo;
import com.obast.charer.qo.CameraQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务接口
 */
public interface ICameraManagerService {
    Paging<CameraVo> queryPageList(PageRequest<CameraQueryBo> pageRequest);

    List<CameraVo> queryList(PageRequest<CameraQueryBo> pageRequest);

    CameraVo queryDetail(String CameraId);

    void addCamera(CameraBo data);

    void updateCamera(CameraBo data);

    void updateStatus(CameraBo bo);

    void bindStation(CameraBo bo);

    boolean deleteCamera(String id);

    boolean batchDeleteCamera(List<String> ids);


    Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request);

}
