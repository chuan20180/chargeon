package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ISmsConfigData;
import com.obast.charer.system.dto.bo.SmsConfigBo;
import com.obast.charer.system.dto.vo.sms.SmsConfigVo;
import com.obast.charer.system.service.system.ISmsConfigManagerService;
import com.obast.charer.model.sms.SmsConfig;
import com.obast.charer.qo.SmsConfigQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：短信配置管理服务实现
 */
@Service
public class SmsConfigManagerServiceImpl implements ISmsConfigManagerService {

    @Autowired
    private ISmsConfigData smsConfigData;

    @Override
    public Paging<SmsConfigVo> queryPageList(PageRequest<SmsConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(smsConfigData.findPage(pageRequest), SmsConfigVo.class);
    }

    @Override
    public List<SmsConfigVo> queryList(PageRequest<SmsConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(smsConfigData.findList(pageRequest.getData()), SmsConfigVo.class);
    }

    @Override
    public SmsConfigVo queryDetail(String sysConfigId) {
        return MapstructUtils.convert(smsConfigData.findById(sysConfigId), SmsConfigVo.class);
    }

    @Override
    public boolean update(SmsConfigBo bo) {
        SmsConfig di = bo.to(SmsConfig.class);
        return smsConfigData.save(di) != null;
    }

    @Override
    public void updateStatus(SmsConfigBo bo) {
        SmsConfig smsConfig = smsConfigData.findById(bo.getId());
        smsConfig.setStatus(bo.getStatus());
        smsConfigData.save(smsConfig);
    }
}