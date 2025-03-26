package com.obast.charer.system.service.monitor.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.data.business.IAlertConfigData;
import com.obast.charer.data.business.IAlertRecordData;
import com.obast.charer.model.alert.AlertConfig;
import com.obast.charer.model.alert.AlertRecord;
import com.obast.charer.qo.AlertConfigQueryBo;
import com.obast.charer.qo.AlertRecordQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlertService {

    @Autowired
    private IAlertConfigData alertConfigData;
    @Autowired
    private IAlertRecordData alertRecordData;

    public AlertConfig createAlertConfig(AlertConfig alertConfig) {
        return alertConfigData.save(alertConfig);
    }

    public AlertConfig updateAlertConfig(AlertConfig alertConfig) {
        return alertConfigData.save(alertConfig);
    }

    public void deleteAlertConfigById(String id) {
        alertConfigData.deleteById(id);
    }

    public Paging<AlertConfig> selectAlertConfigPage(PageRequest<AlertConfigQueryBo> request) {
        return alertConfigData.findPage(request);
    }

    public Paging<AlertRecord> selectAlertRecordPage(PageRequest<AlertRecordQueryBo> request) {
        return alertRecordData.findPage(request);
    }

    public void addAlert(AlertConfig config, String content) {
        alertRecordData.save(AlertRecord.builder()
                .level(config.getLevel())
                .name(config.getName())
                .readFlg(false)
                .alertTime(System.currentTimeMillis())
                .details(content)
                .build());
    }
}
