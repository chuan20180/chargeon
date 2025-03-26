package com.obast.charer.temporal;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.model.stats.TimeData;
import com.obast.charer.qo.DeviceLogQueryBo;

import java.util.List;

public interface IThingModelMessageData {

    Paging<ThingModelMessage> findPage(PageRequest<DeviceLogQueryBo> request);

    /**
     * 按消息类型和标识符取设备消息
     *
     * @param deviceId   设备id
     * @param type       消息类型
     * @param identifier 标识符
     * @param page       页码
     * @param size       页大小
     */
    Paging<ThingModelMessage> findByTypeAndIdentifier(String deviceId, String type,  String identifier, int page, int size);



    /**
     * 按用户统计时间段内每小时上报次数
     *
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    List<TimeData> getDeviceMessageStatsWithUid(String uid, long start, long end);

    /**
     * 按用户统计时间段内上行消息
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    List<TimeData> getDeviceUpMessageStatsWithUid(String uid, Long start, Long end);

    /**
     * 按用户统计时间段内下行
     * @param uid   用户id
     * @param start 开始时间戳
     * @param end   结束时间戳
     */
    List<TimeData> getDeviceDownMessageStatsWithUid(String uid, Long start, Long end);


    /**
     * 查询指定设备集类型并按时间倒序
     *
     * @param deviceIds   设备ids
     * @param type       消息类型
     * @param identifier 标识符
     * @param page       页码
     * @param size       页大小
     */
    Paging<ThingModelMessage> findByTypeAndDeviceIds(List<String> deviceIds, String type,
                                                      String identifier, int page, int size);

    void add(ThingModelMessage msg);

    long count();
}
