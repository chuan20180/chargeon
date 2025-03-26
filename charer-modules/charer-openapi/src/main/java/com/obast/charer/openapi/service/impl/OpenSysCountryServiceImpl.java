package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ISysCountryData;
import com.obast.charer.openapi.dto.vo.OpenSysCountryVo;
import com.obast.charer.openapi.service.IOpenSysCountryService;
import com.obast.charer.qo.SysCountryQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenSysCountryServiceImpl implements IOpenSysCountryService {

    @Autowired
    private ISysCountryData sysCountryData;

    @Override
    public List<OpenSysCountryVo> queryList(SysCountryQueryBo queryBo) {
        return MapstructUtils.convert(sysCountryData.findList(queryBo), OpenSysCountryVo.class);
    }
}
