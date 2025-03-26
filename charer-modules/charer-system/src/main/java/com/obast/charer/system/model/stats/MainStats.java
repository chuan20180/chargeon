package com.obast.charer.system.model.stats;

import com.obast.charer.model.stats.DataItem;
import com.obast.charer.model.stats.TimeData;
import lombok.Data;

import java.util.List;

/**
 * 首页数据统计
 */
@Data
public class MainStats {

    /**
     * 品类数量
     */
    private long categoryTotal;

    /**
     * 产品数量
     */
    private long productTotal;

    /**
     * 设备数量
     */
    private long deviceTotal;

    /**
     * 上报数量
     */
    private long reportTotal;

    /**
     * 在线数量
     */
    private long onlineTotal;

    /**
     * 离线数量
     */
    private long offlineTotal;

    /**
     * 待激活设备
     */
    private long neverOnlineTotal;

    /**
     * 上报数据数量统计
     */
    private List<TimeData> reportDataStats;


    /**
     * 上行数据数量统计
     */
    private List<TimeData> deviceUpMessageStats;

    /**
     * 下行数据数量统计
     */
    private List<TimeData> deviceDownMessageStats;


    /**
     * 按品类统计的设备数量
     */
    private List<DataItem> deviceStatsOfCategory;




}
