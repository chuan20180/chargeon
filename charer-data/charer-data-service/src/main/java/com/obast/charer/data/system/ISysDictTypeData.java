package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysDictType;
import com.obast.charer.qo.SysDictTypeQueryBo;

/**
 * 字典类型数据接口
 *
 * @author sjg
 */
public interface ISysDictTypeData  extends ICommonData<SysDictType, String>, IJPACommonData<SysDictType, SysDictTypeQueryBo, String> {

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    SysDictType findByDicType(String dictType);

    /**
     * 修改字典类型
     *
     * @param dictType 类型
     * @param newType  新类型
     */
    void updateDicType(String dictType, String newType);

    boolean checkDictTypeUnique(SysDictType dictType);
}
