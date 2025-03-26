package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.CacheNames;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.system.ISysDictData;
import com.obast.charer.system.dto.bo.SysDictDataBo;
import com.obast.charer.system.dto.vo.SysDictDataVo;
import com.obast.charer.system.service.system.ISysDictDataService;
import com.obast.charer.model.system.SysDictData;
import com.obast.charer.qo.SysDictDataQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysDictDataServiceImpl implements ISysDictDataService {

    private final ISysDictData sysDictData;

    @Override
    public Paging<SysDictDataVo> queryPageList(PageRequest<SysDictDataQueryBo> pageRequest) {
        Paging<SysDictData> pageList = sysDictData.findPage(pageRequest);
        Paging<SysDictDataVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysDictData sysDictData: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysDictData));
        }
        return newPageList;
    }

    @Override
    public List<SysDictDataVo> queryList(PageRequest<SysDictDataQueryBo> pageRequest) {
        List<SysDictData> list = sysDictData.findList(pageRequest.getData());
        List<SysDictDataVo> newList = new ArrayList<>();
        for(SysDictData sysDictData: list) {
            newList.add(fillData(sysDictData));
        }
        return newList;
    }

    @Override
    public SysDictDataVo queryDetail(String id) {
        return fillData(sysDictData.findById(id));
    }

    private SysDictDataVo fillData(SysDictData sysDictData) {
        return MapstructUtils.convert(sysDictData, SysDictDataVo.class);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictDataVo selectDictDataById(String dictCode) {
        return MapstructUtils.convert(sysDictData.findById(dictCode), SysDictDataVo.class);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(String[] dictCodes) {
        for (String dictCode : dictCodes) {
            SysDictData data = sysDictData.findById(dictCode);
            sysDictData.deleteById(dictCode);
//            CacheUtils.evict(CacheNames.SYS_DICT, data.getDictType());
        }
    }

    /**
     * 新增保存字典数据信息
     *
     * @param bo 字典数据信息
     * @return 结果
     */
    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    public List<SysDictDataVo> insertDictData(SysDictDataBo bo) {
        SysDictData data = MapstructUtils.convert(bo, SysDictData.class);
        sysDictData.save(data);
        return MapstructUtils.convert(sysDictData.findByDicType(data.getDictType()), SysDictDataVo.class);
    }

    /**
     * 修改保存字典数据信息
     *
     * @param bo 字典数据信息
     * @return 结果
     */
//    @CachePut(cacheNames = CacheNames.SYS_DICT, key = "#bo.dictType")
    @Override
    public List<SysDictDataVo> updateDictData(SysDictDataBo bo) {
        SysDictData data = MapstructUtils.convert(bo, SysDictData.class);
        sysDictData.save(data);
        return MapstructUtils.convert(sysDictData.findByDicType(data.getDictType()), SysDictDataVo.class);
    }

}
