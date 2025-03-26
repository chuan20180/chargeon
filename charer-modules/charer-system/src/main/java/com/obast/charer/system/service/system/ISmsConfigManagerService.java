package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SmsConfigBo;
import com.obast.charer.system.dto.vo.sms.SmsConfigVo;
import com.obast.charer.qo.SmsConfigQueryBo;

import java.util.List;

public interface ISmsConfigManagerService {
    Paging<SmsConfigVo> queryPageList(PageRequest<SmsConfigQueryBo> pageRequest);

    List<SmsConfigVo> queryList(PageRequest<SmsConfigQueryBo> pageRequest);

    SmsConfigVo queryDetail(String sysConfigId);

    boolean update(SmsConfigBo data);

    void updateStatus(SmsConfigBo bo);
}
