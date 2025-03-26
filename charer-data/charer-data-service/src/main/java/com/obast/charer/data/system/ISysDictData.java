package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysDictData;
import com.obast.charer.qo.SysDictDataQueryBo;

import java.util.List;

/**
 * 字典数据接口
 *
 * @author sjg
 */
public interface ISysDictData  extends ICommonData<SysDictData, String>, IJPACommonData<SysDictData, SysDictDataQueryBo, String> {



    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> findByDicType(String dictType);

    /**
     * 根据字典类型查询字典数据数量
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    long countByDicType(String dictType);

}
