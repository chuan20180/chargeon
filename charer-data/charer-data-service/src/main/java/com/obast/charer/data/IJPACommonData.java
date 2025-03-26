package com.obast.charer.data;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.model.Id;

import java.util.List;

/**
 * 数据基础服务接口
 */
public interface IJPACommonData<T extends Id<ID>, QO, ID> {

    Paging<T> findPage(PageRequest<QO> pageRequest) ;

    List<T> findList(QO queryBo);


}
