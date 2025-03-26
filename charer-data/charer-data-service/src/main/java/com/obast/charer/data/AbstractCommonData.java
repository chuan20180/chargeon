package com.obast.charer.data;

import com.obast.charer.common.api.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据基础服务接口
 */
public abstract class AbstractCommonData<QO> {

    Sort.Order defaultSort = Sort.Order.desc("createTime");

    protected Sort processSort(PageRequest<QO> request) {
        List<Sort.Order> orders = new ArrayList<>();
        Map<String, String> sortMap = request.getSortMap();
        if(sortMap != null) {
            sortMap.forEach((key, value) -> orders.add(value.equals("desc") ? Sort.Order.desc(key) : Sort.Order.asc(key)));
        }
        if(orders.isEmpty()) {
            orders.add(defaultSort);
        }
        return Sort.by(orders);
    }

    protected org.springframework.data.domain.PageRequest processPageSort(PageRequest<QO> request) {
        return org.springframework.data.domain.PageRequest.of(request.getPageNum()-1, request.getPageSize(), processSort(request));
    }
}
