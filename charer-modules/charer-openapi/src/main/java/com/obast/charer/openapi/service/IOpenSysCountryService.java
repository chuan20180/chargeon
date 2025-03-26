package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenSysCountryVo;
import com.obast.charer.qo.SysCountryQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenSysCountryService {


    List<OpenSysCountryVo> queryList(SysCountryQueryBo bo);



}
