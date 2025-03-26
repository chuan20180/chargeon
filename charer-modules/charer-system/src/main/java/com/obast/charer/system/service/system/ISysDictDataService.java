package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysDictDataBo;
import com.obast.charer.system.dto.vo.SysDictDataVo;
import com.obast.charer.qo.SysDictDataQueryBo;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author Lion Li
 */
public interface ISysDictDataService {

    Paging<SysDictDataVo> queryPageList(PageRequest<SysDictDataQueryBo> pageRequest);

    List<SysDictDataVo> queryList(PageRequest<SysDictDataQueryBo> pageRequest);

    SysDictDataVo queryDetail(String id);


    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    SysDictDataVo selectDictDataById(String dictCode);

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    void deleteDictDataByIds(String[] dictCodes);

    /**
     * 新增保存字典数据信息
     *
     * @param bo 字典数据信息
     * @return 结果
     */
    List<SysDictDataVo> insertDictData(SysDictDataBo bo);

    /**
     * 修改保存字典数据信息
     *
     * @param bo 字典数据信息
     * @return 结果
     */
    List<SysDictDataVo> updateDictData(SysDictDataBo bo);
}
