package com.obast.charer.system.task;


import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 设备状态检查定时任务
 */
@Slf4j
@Component
public class DeviceStateCheckTask {

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IProductData productData;

    @Scheduled(fixedDelay = 30, initialDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void syncState() {
        //取出数据库中所有在线设备
        List<Charger> chargers = chargerData.findAll();
        //判断属性更新时间是否大于产品定义保活时长
        for (Charger charger : chargers) {
            Product product = productData.findByProductKey(charger.getProductKey());
            Long keepAliveTime = product.getKeepAliveTime();
            if (keepAliveTime == null) {
                continue;
            }

            //最后更新时间超时保活时长1.1倍认为设备离线了
            if (charger.getLastUpdateTime() == null || System.currentTimeMillis() - charger.getLastUpdateTime() > keepAliveTime * 1000 * 1.5) {
                if (charger.getOnline() != null && charger.getOnline().equals(OnlineStatusEnum.Offline)) {
                    continue;
                }
                log.info("[系统定时任务]设备状态检测，设置已离线 ,deviceDn={}", charger.getDn());
                //更新为离线
                charger.setOnline(OnlineStatusEnum.Offline);
                charger.setOfflineTime(System.currentTimeMillis());
                chargerData.save(charger);

                List<ChargerGun> chargerGuns = chargerGunData.findByChargerId(charger.getId());
                for(ChargerGun chargerGun: chargerGuns) {
                    chargerGun.setState(ChargerGunStateEnum.Unknow);
                    chargerGunData.save(chargerGun);
                }
            }

            //最后更新时间超时保活时长1.1倍认为设备离线了
            if (charger.getLastUpdateTime() != null && System.currentTimeMillis() - charger.getLastUpdateTime() < keepAliveTime * 1000) {
                if (charger.getOnline() != null && charger.getOnline().equals(OnlineStatusEnum.Online)) {
                    continue;
                }
                log.info("[系统定时任务]设备状态检测，设置在线 ,deviceDn={}", charger.getDn());
                //更新为离线
                charger.setOnline(OnlineStatusEnum.Online);
                charger.setOnlineTime(System.currentTimeMillis());
                chargerData.save(charger);
            }
        }
    }
}
