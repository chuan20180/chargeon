package com.obast.charer.plugin.service.impl;

import com.obast.charer.api.system.feign.IRemoteParkService;
import com.obast.charer.common.IDeviceAction;
import com.obast.charer.common.InvokeType;
import com.obast.charer.common.ProductType;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.model.DeviceImage;
import com.obast.charer.common.oss.core.OssClient;
import com.obast.charer.common.oss.entity.UploadResult;
import com.obast.charer.common.oss.factory.OssFactory;
import com.obast.charer.common.plugin.core.thing.dcam.actions.*;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.utils.UniqueIdUtil;
import com.obast.charer.common.plugin.core.thing.DeviceState;
import com.obast.charer.common.plugin.core.thing.DeviceStatus;
import com.obast.charer.common.plugin.core.thing.ThingDevice;
import com.obast.charer.common.plugin.core.thing.ThingProduct;
import com.obast.charer.data.business.*;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.data.system.ISysOssData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.enums.ParkStateEnum;
import com.obast.charer.model.device.Dcam;
import com.obast.charer.model.device.DcamParking;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.park.Park;
import com.obast.charer.model.parking.Parking;
import com.obast.charer.model.product.Product;
import com.obast.charer.model.station.Station;
import com.obast.charer.model.system.SysOss;
import com.obast.charer.common.plugin.core.thing.charger.message.LoginAckMessage;
import com.obast.charer.common.plugin.core.thing.dcam.DcamAction;
import com.obast.charer.common.plugin.core.thing.dcam.DcamDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.dcam.IDcamService;
import com.obast.charer.common.plugin.core.thing.dcam.message.DcamCarInEventMessage;
import com.obast.charer.common.plugin.core.thing.dcam.message.DcamCarOutEventMessage;

import com.obast.charer.plugin.DeviceRouter;
import com.obast.charer.plugin.IPluginMain;
import com.obast.charer.plugin.PluginRouter;
import com.obast.charer.qo.ParkQueryBo;

import cn.hutool.core.codec.Base64Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.*;

@Slf4j
@Service
public class DcamEventServiceImpl implements IDcamService {

    private final ProductType productType = ProductType.DCAM;

    @Autowired
    private IProductData productData;

    @Autowired
    private IDcamData dcamData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IParkingData parkingData;

    @Autowired
    private IParkData parkData;

    @Autowired
    private IDcamParkingData dcamParkingData;

    @Autowired
    private ISysOssData sysOssData;

    @Autowired
    private IRemoteParkService remoteParkService;


    @Autowired
    private DeviceRouter deviceRouter;

    @Override
    public ActionResult<?> post(String pluginId, DcamAction<?> action) {
        try {
            //log.info("[服务器端调试]收到设备消息:{}, type: {} action:{}", pluginId, action.getType().name(), action);
            String deviceDn = action.getDn();
            ThingDevice thingDevice = getDevice(deviceDn);
            if (thingDevice == null) {
                log.debug("[服务器端调试]设备不存在(deviceDn: {})", deviceDn);
                return ActionResult.builder().code(ErrCode.DEVICE_NOT_FOUND.getKey()).reason(ErrCode.DEVICE_NOT_FOUND.getValue()).build();
            }

            if(thingDevice.getStatus() != DeviceStatus.Enabled) {
                log.debug("[服务器端调试]设备未启用(deviceDn:{})", deviceDn);
                return ActionResult.builder().code(ErrCode.DEVICE_NOT_ENABLED.getKey()).reason(ErrCode.DEVICE_NOT_ENABLED.getValue()).build();
            }

            String routeId = String.format("%s-%s", productType.name(), deviceDn);
            //添加设备路由
            deviceRouter.putRouter(routeId, new PluginRouter(IPluginMain.MAIN_ID, pluginId));

            DcamDirectiveEnum directive = (DcamDirectiveEnum) action.getDirective();
            ActionResult<?> result;
            switch (directive) {

                //设备登陆
                case REGISTER_EVENT:
                    result = register(thingDevice, (DcamRegisterEventAction<?>) action);
                    break;

                //设备心跳
                case PING_EVENT:
                    result = ping(thingDevice, (DcamPingEventAction<?>) action);
                    break;

                //上下线
                case STATE_EVENT:
                    result = stateChange(thingDevice, (DcamStateEventAction<?>) action);
                    break;

                //serialData
                case SERIAL_DATA_EVENT:
                    result = serialData(thingDevice, (DcamSerialDataEventAction<?>) action);
                    break;

                case CAR_IN_EVENT:
                    result = carIn(thingDevice, (DcamCarInEventAction<?>) action);
                    break;

                case CAR_OUT_EVENT:
                    result = carOut(thingDevice, (DcamCarOutEventAction<?>) action);
                    break;

                default:
                    log.error("[服务器端调试]未处理的请求 pk={}, dn={}, action: {}", action.getPk(), action.getDn(), action);
                    result = ActionResult.builder().code(ErrCode.PARAMS_EXCEPTION.getKey()).reason(ErrCode.PARAMS_EXCEPTION.getValue()).build();
                    break;
            }

            publishMsg(
                    thingDevice,
                    action,
                    ThingModelMessage.builder()
                            .invoke(InvokeType.Event.name())
                            .directive(InvokeType.Event.name())
                            .type(ProductType.DCAM.name())
                            .identifier(directive.name())
                            .data(action.getData())
                            .time(System.currentTimeMillis())
                            .build()
            );


            return result;

        } catch (Throwable e) {
            log.error("action process error", e);
            return ActionResult.builder().code(1).reason(e.getMessage()).build();
        }
    }

    @Override
    public void notify(String s, DcamAction<?> dcamAction) {

    }

    @Override
    public ThingProduct getProduct(String pk) {
        try {
            Product product = productData.findByProductKey(pk);
            if (product == null) {
                return null;
            }
            return ThingProduct.builder()
                    .category(product.getCategory())
                    .productKey(product.getProductKey())
                    .name(product.getName())
                    .productSecret(product.getProductSecret())
                    .build();
        } catch (Throwable e) {
            log.error("get product error", e);
            return null;
        }
    }

    @Override
    public ThingDevice getDevice(String dn) {
        try {
            Dcam dcam = dcamData.findByDn(dn);
            if (dcam == null) {
                return null;
            }
            return ThingDevice.builder()
                    .productKey(dcam.getProductKey())
                    .productType(productType)
                    .deviceDn(dcam.getDn())
                    .model(dcam.getModel())
                    .secret(dcam.getSecret())
                    .status(DeviceStatus.valueOf(dcam.getStatus().name()))
                    .build();
        } catch (Throwable e) {
            log.error("get device error", e);
            return null;
        }
    }

    /*
     * 注册
     */
    private ActionResult<?> register(ThingDevice thingDevice, DcamRegisterEventAction<?> action) {
        log.debug("[服务器端调试]登陆请求 action: {}", action);

        if(action.getUsername() == null || action.getPassword() == null) {
            return ActionResult.builder().code(ErrCode.DEVICE_AUTH_NULL.getKey()).reason(ErrCode.DEVICE_AUTH_NULL.getValue()).build();
        }

        if(!Objects.equals(action.getUsername(), thingDevice.getDeviceDn()) || !Objects.equals(action.getPassword(), thingDevice.getSecret())) {
            return ActionResult.builder().code(ErrCode.DEVICE_AUTH_INVALID.getKey()).reason(ErrCode.DEVICE_AUTH_INVALID.getValue()).build();
        }

        log.debug("[服务器端调试]登陆成功 dn: {}, action: {}", thingDevice.getDeviceDn(), action);
        LoginAckMessage message = new LoginAckMessage((byte) 0);
        return new ActionResult<>(0, message);
    }

    /*
     * 心跳包
     */
    private ActionResult<?> ping(ThingDevice thingDevice, DcamPingEventAction<?> action) {
        log.debug("[服务器端调试]心跳请求 dn={}, action={}", thingDevice.getDeviceDn(), action);
        Dcam dcam = dcamData.findByDn(thingDevice.getDeviceDn());
        if(dcam == null) {
            log.error("[服务器端调试] code={}, msg={}", ErrCode.DCAM_NOT_FOUND.getKey(), ErrCode.DCAM_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_NOT_FOUND.getKey()).reason(ErrCode.DCAM_NOT_FOUND.getValue()).build();
        }
        dcam.setOnline(OnlineStatusEnum.Online);
        dcam.setOnlineTime(System.currentTimeMillis());
        dcamData.save(dcam);
        return new ActionResult<>(0);
    }

    /*
     * 上下线消息
     */
    private ActionResult<?> stateChange(ThingDevice thingDevice, DcamStateEventAction<?> action) {
        log.debug("[服务器端调试]收到上下线消息数据 device Dn={},{}", thingDevice.getDeviceDn(), action);

        Dcam dcam = dcamData.findByDn(thingDevice.getDeviceDn());
        if(dcam == null) {
            log.error("[服务器端调试] code={}, msg={}", ErrCode.DCAM_NOT_FOUND.getKey(), ErrCode.DCAM_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_NOT_FOUND.getKey()).reason(ErrCode.DCAM_NOT_FOUND.getValue()).build();
        }

        DeviceState state = action.getState();
        if (state == DeviceState.ONLINE) {
            dcam.setOnline(OnlineStatusEnum.Online);
            dcam.setOnlineTime(System.currentTimeMillis());
        } else {
            dcam.setOnline(OnlineStatusEnum.Offline);
            dcam.setOfflineTime(System.currentTimeMillis());
        }

        dcamData.save(dcam);
        return new ActionResult<>(0);
    }

    private ActionResult<?> serialData(ThingDevice dcam, DcamSerialDataEventAction<?> action) {
        //log.debug("[服务器端调试]serialData请求 action: {}", action);
        return new ActionResult<>(0);
    }

    /*
     * serialData
     */
    public ActionResult<?> carIn(ThingDevice thingDevice, DcamCarInEventAction<?> action) {
        log.debug("[服务器端调试] car in 请求 dn={}", thingDevice.getDeviceDn());

        DcamCarInEventMessage message = (DcamCarInEventMessage) action.getData();

        Dcam dcam = dcamData.findByDn(thingDevice.getDeviceDn());
        if(dcam == null) {
            log.error("[服务器端调试] code={}, msg={}", ErrCode.DCAM_NOT_FOUND.getKey(), ErrCode.DCAM_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_NOT_FOUND.getKey()).reason(ErrCode.DCAM_NOT_FOUND.getValue()).build();
        }

        String stationId = dcam.getStationId();
        if(stationId == null) {
            log.error("[服务器端调试] car in 异常, Dcam DN={}, reason={}", dcam.getDn(), ErrCode.DCAM_NOT_BIND_STATION.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_NOT_BIND_STATION.getKey()).reason(ErrCode.DCAM_NOT_BIND_STATION.getValue()).build();
        }

        Station station = stationData.findById(stationId);
        if(station == null) {
            log.error("[服务器端调试] car in 异常, Dcam DN={}, reason={}", dcam.getDn(), ErrCode.STATION_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.STATION_NOT_FOUND.getKey()).reason(ErrCode.STATION_NOT_FOUND.getValue()).build();
        }

        if(!station.getStatus().equals(EnableStatusEnum.Enabled)) {
            log.error("[服务器端调试] car in 异常, Dcam DN={}, reason={}", dcam.getDn(), ErrCode.STATION_NOT_ENABLED.getValue());
            return ActionResult.builder().code(ErrCode.STATION_NOT_ENABLED.getKey()).reason(ErrCode.STATION_NOT_ENABLED.getValue()).build();
        }

        String parkName = message.getZoneName();

        if(StringUtils.isBlank(parkName)) {
            log.error("[服务器端调试] car in 异常, Dcam DN={}, reason={}", dcam.getDn(), ErrCode.DCAM_PARKING_NAME_EMPTY.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_PARKING_NAME_EMPTY.getKey()).reason(ErrCode.DCAM_PARKING_NAME_EMPTY.getValue()).build();
        }

        DcamParking dcamParking = dcamParkingData.findByDcamIdAndName(dcam.getId(), parkName);
        if(dcamParking == null) {
            log.error("[服务器端调试] car in 异常, Dcam DN={}, 车位名称={}, reason={}", dcam.getDn(), parkName, ErrCode.DCAM_PARKING_NAME_NOT_SET.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_PARKING_NAME_NOT_SET.getKey()).reason(ErrCode.DCAM_PARKING_NAME_NOT_SET.getValue()).build();
        }

        String parkingId = dcamParking.getParkingId();
        if(StringUtils.isBlank(parkingId)) {
            log.error("[服务器端调试] car in 异常, Dcam DN={}, 车位={}, reason={}", dcam.getDn(), parkName, ErrCode.DCAM_PARKING_PARKING_NOT_BIND.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_PARKING_PARKING_NOT_BIND.getKey()).reason(ErrCode.DCAM_PARKING_PARKING_NOT_BIND.getValue()).build();
        }

        Parking parking = parkingData.findById(parkingId);
        if(parking == null) {
            log.error("[服务器端调试] car in 异常, Dcam DN={}, reason={}", dcam.getDn(), ErrCode.PARKING_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.PARKING_NOT_FOUND.getKey()).reason(ErrCode.PARKING_NOT_FOUND.getValue()).build();
        }

        List<String> bgImages = new ArrayList<>();
        if(action.getBgImages() != null) {
            for(DeviceImage deviceImage: action.getBgImages()) {
                byte[] imageBytes = Base64Decoder.decode(deviceImage.getBase64Image());
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                try {
                    String fileName = deviceImage.getName();
                    String suffix = deviceImage.getType();
                    MultipartFile file = new MockMultipartFile(fileName, fileName, "image/jpg", byteArrayInputStream);

                    OssClient storage = OssFactory.instance();
                    UploadResult uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
                    // 保存文件信息
                    SysOss oss = new SysOss();
                    oss.setUrl(uploadResult.getUrl());
                    oss.setFileSuffix(suffix);
                    oss.setFileName(uploadResult.getFilename());
                    oss.setOriginalName(fileName);
                    oss.setService(storage.getConfigKey());
                    sysOssData.save(oss);
                    bgImages.add(oss.getUrl());
                } catch (Exception e) {
                    log.error("[服务器端调试] car in 保存bg image异常, Dcam DN={}, reason={}", dcam.getDn(), e.getLocalizedMessage());
                }
            }
        }

        List<String> fetureImages = new ArrayList<>();
        if(action.getFetureImages() != null) {
            for(DeviceImage deviceImage: action.getFetureImages()) {
                byte[] imageBytes = Base64Decoder.decode(deviceImage.getBase64Image());
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                try {
                    String fileName = deviceImage.getName();
                    String suffix = deviceImage.getType();
                    MultipartFile file = new MockMultipartFile(fileName, fileName, "image/jpg", byteArrayInputStream);

                    OssClient storage = OssFactory.instance();
                    UploadResult uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
                    // 保存文件信息
                    SysOss oss = new SysOss();
                    oss.setUrl(uploadResult.getUrl());
                    oss.setFileSuffix(suffix);
                    oss.setFileName(uploadResult.getFilename());
                    oss.setOriginalName(fileName);
                    oss.setService(storage.getConfigKey());
                    sysOssData.save(oss);
                    fetureImages.add(oss.getUrl());
                } catch (Exception e) {
                    log.error("[服务器端调试] car in 保存bg image异常, Dcam DN={}, reason={}", dcam.getDn(), e.getLocalizedMessage());
                }
            }
        }

        String plate = message.getPlate();
        Integer plateType = message.getPlateType();
        Date time = message.getTime();

        Park park = new Park();
        park.setParkingId(parking.getId());
        park.setParkingNo(parking.getNo());
        park.setParkingName(parking.getName());
        park.setStationId(station.getId());
        park.setStationAddress(station.getAddress());
        park.setStationName(station.getName());

        park.setInTime(time);
        park.setInBgImage(bgImages);
        park.setInBgImage(fetureImages);

        park.setCarPlate(plate);
        park.setCarPlateType(plateType);
        park.setState(ParkStateEnum.Entered);
        park.setTenantId(station.getTenantId());
        park.setAgentId(station.getAgentId());
        parkData.add(park);

        log.error("[服务器端调试] car in 入场成功, dn={}, plate={}", dcam.getDn(), plate);
        return new ActionResult<>(0);
    }

    public ActionResult<?> carOut(ThingDevice thingDevice, DcamCarOutEventAction<?> action) {
        log.debug("[服务器端调试] car out 请求 dn={}", thingDevice.getDeviceDn());

        DcamCarOutEventMessage message = (DcamCarOutEventMessage) action.getData();

        Dcam dcam = dcamData.findByDn(thingDevice.getDeviceDn());
        if(dcam == null) {
            log.error("[服务器端调试] code={}, msg={}", ErrCode.DCAM_NOT_FOUND.getKey(), ErrCode.DCAM_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_NOT_FOUND.getKey()).reason(ErrCode.DCAM_NOT_FOUND.getValue()).build();
        }

        String stationId = dcam.getStationId();
        if(stationId == null) {
            log.error("[服务器端调试] car out 异常 {}", ErrCode.DCAM_NOT_BIND_STATION.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_NOT_BIND_STATION.getKey()).reason(ErrCode.DCAM_NOT_BIND_STATION.getValue()).build();
        }

        Station station = stationData.findById(stationId);
        if(station == null) {
            log.error("[服务器端调试] car out 异常 {}", ErrCode.STATION_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.STATION_NOT_FOUND.getKey()).reason(ErrCode.STATION_NOT_FOUND.getValue()).build();
        }

        if(!station.getStatus().equals(EnableStatusEnum.Enabled)) {
            log.error("[服务器端调试] car out 异常 {}", ErrCode.STATION_NOT_ENABLED.getValue());
            return ActionResult.builder().code(ErrCode.STATION_NOT_ENABLED.getKey()).reason(ErrCode.STATION_NOT_ENABLED.getValue()).build();
        }

        String parkName = message.getZoneName();

        if(StringUtils.isBlank(parkName)) {
            log.error("[服务器端调试] car out 异常 {}", ErrCode.DCAM_PARKING_NAME_EMPTY.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_PARKING_NAME_EMPTY.getKey()).reason(ErrCode.DCAM_PARKING_NAME_EMPTY.getValue()).build();
        }

        DcamParking dcamParking = dcamParkingData.findByDcamIdAndName(dcam.getId(), parkName);
        if(dcamParking == null) {
            log.error("[服务器端调试] car out 异常 {}", ErrCode.DCAM_PARKING_NAME_NOT_SET.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_PARKING_NAME_NOT_SET.getKey()).reason(ErrCode.DCAM_PARKING_NAME_NOT_SET.getValue()).build();
        }

        String parkingId = dcamParking.getParkingId();
        if(StringUtils.isBlank(parkingId)) {
            log.error("[服务器端调试] car out 异常 {}", ErrCode.DCAM_PARKING_PARKING_NOT_BIND.getValue());
            return ActionResult.builder().code(ErrCode.DCAM_PARKING_PARKING_NOT_BIND.getKey()).reason(ErrCode.DCAM_PARKING_PARKING_NOT_BIND.getValue()).build();
        }

        Parking parking = parkingData.findById(parkingId);
        if(parking == null) {
            log.error("[服务器端调试] car out 异常 {}", ErrCode.PARKING_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.PARKING_NOT_FOUND.getKey()).reason(ErrCode.PARKING_NOT_FOUND.getValue()).build();
        }

        List<String> bgImages = new ArrayList<>();
        if(action.getBgImages() != null) {
            for(DeviceImage deviceImage: action.getBgImages()) {
                byte[] imageBytes = Base64Decoder.decode(deviceImage.getBase64Image());
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                try {
                    String fileName = deviceImage.getName();
                    String suffix = deviceImage.getType();
                    MultipartFile file = new MockMultipartFile(fileName, fileName, "image/jpg", byteArrayInputStream);

                    OssClient storage = OssFactory.instance();
                    UploadResult uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
                    // 保存文件信息
                    SysOss oss = new SysOss();
                    oss.setUrl(uploadResult.getUrl());
                    oss.setFileSuffix(suffix);
                    oss.setFileName(uploadResult.getFilename());
                    oss.setOriginalName(fileName);
                    oss.setService(storage.getConfigKey());
                    sysOssData.save(oss);
                    bgImages.add(oss.getUrl());
                } catch (Exception e) {
                    log.error("[服务器端调试] car in 保存bg image异常, Dcam DN={}, reason={}", dcam.getDn(), e.getLocalizedMessage());
                }

            }
        }

        List<String> fetureImages = new ArrayList<>();
        if(action.getFetureImages() != null) {
            for(DeviceImage deviceImage: action.getFetureImages()) {
                byte[] imageBytes = Base64Decoder.decode(deviceImage.getBase64Image());
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
                try {
                    String fileName = deviceImage.getName();
                    String suffix = deviceImage.getType();
                    MultipartFile file = new MockMultipartFile(fileName, fileName, "image/jpg", byteArrayInputStream);

                    OssClient storage = OssFactory.instance();
                    UploadResult uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
                    // 保存文件信息
                    SysOss oss = new SysOss();
                    oss.setUrl(uploadResult.getUrl());
                    oss.setFileSuffix(suffix);
                    oss.setFileName(uploadResult.getFilename());
                    oss.setOriginalName(fileName);
                    oss.setService(storage.getConfigKey());
                    sysOssData.save(oss);
                    fetureImages.add(oss.getUrl());
                } catch (Exception e) {
                    log.error("[服务器端调试] car in 保存bg image异常, Dcam DN={}, reason={}", dcam.getDn(), e.getLocalizedMessage());
                }
            }
        }

        String plate = message.getPlate();
        Date time = message.getTime();


        ParkQueryBo parkQueryBo = new ParkQueryBo();
        parkQueryBo.setStationId(station.getId());
        parkQueryBo.setParkingId(parking.getId());
        parkQueryBo.setCarPlate(plate);
        parkQueryBo.setState(ParkStateEnum.Entered);
        List<Park> parks = parkData.findList(parkQueryBo);
        if(parks == null || parks.size() != 1) {
            return ActionResult.builder().code(ErrCode.PARK_IN_RECORD_NOT_FOUND.getKey()).reason(ErrCode.PARK_IN_RECORD_NOT_FOUND.getValue()).build();
        }

        Park park = parks.get(0);
        park.setState(ParkStateEnum.Appeared);
        park.setOutTime(time);

        park.setOutBgImage(bgImages);
        park.setOutFetureImage(fetureImages);

        park = parkData.update(park);

        //占位费结算
        remoteParkService.settle(park.getId());

        log.info("[服务器端调试] car out 出场成功 , dn={}, plate={}", dcam.getDn(), plate);
        return new ActionResult<>(0);
    }

    private void publishMsg(ThingDevice thingDevice, IDeviceAction<?> action, ThingModelMessage message) {
        try {
            message.setId(UUID.randomUUID().toString());
            message.setMid(UniqueIdUtil.newRequestId());
            message.setDeviceDn(thingDevice.getDeviceDn());
            message.setProductKey(thingDevice.getProductKey());
            message.setDirective(action.getDirective().name());

            if (message.getOccurred() == null) {
                message.setOccurred(action.getTime());
            }
            if (message.getTime() == null) {
                message.setTime(System.currentTimeMillis());
            }
            if (message.getData() == null) {
                message.setData("");
            }

            //producer.publish(Constants.THING_MODEL_MESSAGE_TOPIC, message);
        } catch (Throwable e) {
            log.error("send thing model message error", e);
        }
    }
}
