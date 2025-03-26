package com.obast.charer.system.init;

import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.data.business.IDcamData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
@Service
public class DataInit implements SmartInitializingSingleton {

    @Override
    public void afterSingletonsInstantiated() {
        //等redis实例化后再执行
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {

                    //启动时初始化充电桩状态
                    IChargerData chargerData = SpringUtils.getBean(IChargerData.class);
                    chargerData.initData();

                    //启动时初始化充电枪状态
                    IChargerGunData chargerGunData = SpringUtils.getBean(IChargerGunData.class);
                    chargerGunData.initData();

                    //启动时初始化车位相机状态
                    IDcamData dcamData = SpringUtils.getBean(IDcamData.class);
                    dcamData.initData();

                    log.info("init charger data finished.");

                } catch (
                        Exception e) {
                    log.error("init error", e);
                }
            }
        }, 100);

    }



}
