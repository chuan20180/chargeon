package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.system.dto.bo.CameraBo;
import com.obast.charer.system.dto.vo.device.CameraVo;
import com.obast.charer.temporal.IThingModelMessageData;
import com.obast.charer.data.business.ICameraData;
import com.obast.charer.data.business.IStationData;

import com.obast.charer.system.service.business.ICameraManagerService;
import com.obast.charer.model.device.Camera;
import com.obast.charer.qo.CameraQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务实现
 */
@Service
public class CameraManagerServiceImpl implements ICameraManagerService {

    @Autowired
    private ICameraData cameraData;

    @Lazy
    @Autowired
    private IThingModelMessageData thingModelMessageData;

    @Override
    public Paging<CameraVo> queryPageList(PageRequest<CameraQueryBo> pageRequest) {
        Paging<Camera> pageList = cameraData.findPage(pageRequest);
        Paging<CameraVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Camera Camera: pageList.getRows()) {
            newPageList.getRows().add(fillData(Camera));
        }
        return newPageList;
    }

    @Override
    public List<CameraVo> queryList(PageRequest<CameraQueryBo> pageRequest) {
        List<Camera> list = cameraData.findList(pageRequest.getData());
        List<CameraVo> CameraVos = new ArrayList<>();
        for(Camera Camera: list) {
            CameraVos.add(fillData(Camera));
        }
        return CameraVos;
    }

    @Override
    public CameraVo queryDetail(String cameraId) {
        Camera Camera = cameraData.findById(cameraId);
        return fillData(Camera);
    }

    private CameraVo fillData(Camera camera) {
        CameraVo vo = MapstructUtils.convert(camera, CameraVo.class);
        if(vo == null) {
            return null;
        }



        return vo;
    }

    @Override
    @Transactional
    public void addCamera(CameraBo bo) {
        //同产品不可重复设备名
        Camera CameraRepetition = cameraData.findByDn(bo.getName());
        if (CameraRepetition != null) {
            throw new BizException(ErrCode.DEVICE_ALREADY);
        }

        Camera Camera = bo.to(Camera.class);

        cameraData.add(Camera);
    }

    @Override
    public void updateCamera(CameraBo bo) {
        Camera di = bo.to(Camera.class);
        cameraData.update(di);
    }

    @Override
    public void updateStatus(CameraBo bo) {
        Camera camera = cameraData.findById(bo.getId());
        camera.setStatus(bo.getStatus());
        cameraData.save(camera);
    }

    @Override
    public void bindStation(CameraBo bo) {
        Camera camera = cameraData.findById(bo.getId());
        camera.setStationId(bo.getStationId());
        cameraData.save(camera);
    }

    @Override
    @Transactional
    public boolean deleteCamera(String cameraId) {
        Camera camera = cameraData.findById(cameraId);
        if(camera == null) {
            throw new BizException(ErrCode.DEVICE_NOT_FOUND);
        }
        cameraData.deleteById(cameraId);
        return true;
    }

    @Override
    public boolean batchDeleteCamera(List<String> ids) {
        for(String cameraId: ids) {
            cameraData.deleteById(cameraId);
        }
        return true;
    }

    @Override
    public Paging<ThingModelMessage> logs(PageRequest<DeviceLogQueryBo> request) {
        return thingModelMessageData.findPage(request);
    }
}
