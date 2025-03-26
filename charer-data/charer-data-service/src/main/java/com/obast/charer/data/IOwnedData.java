package com.obast.charer.data;

import com.obast.charer.common.api.Paging;
import com.obast.charer.model.Owned;

import java.util.Collections;
import java.util.List;

/**
 * 数据基础服务接口
 */
public interface IOwnedData<T extends Owned<ID>, ID> extends ICommonData<T, ID> {

    /**
     * 按所属用户取数据
     * @return
     */
    default List findByUid(String uid) {
        return Collections.emptyList();

    }

    default Paging<T> findByUid(String uid, int page, int size) {
        return null;
    }

    /**
     * 按所属用户统计总数
     */
    default long countByUid(String uid) {
        return 0L;

    }

}
