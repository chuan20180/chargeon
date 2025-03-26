package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.ChargerGunBo;
import com.obast.charer.system.dto.vo.device.ChargerGunVo;
import com.obast.charer.qo.ChargerGunQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩管理服务接口
 */
public interface IChargerGunManagerService {
    Paging<ChargerGunVo> queryPageList(PageRequest<ChargerGunQueryBo> pageRequest);

    List<ChargerGunVo> queryList(PageRequest<ChargerGunQueryBo> pageRequest);

    ChargerGunVo queryDetail(String id);

    void add(ChargerGunBo data);

    void update(ChargerGunBo data);

    void bindParking(ChargerGunBo data);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);


}
