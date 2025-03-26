package com.obast.charer.system.service.business;


import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.PriceParkBo;
import com.obast.charer.system.dto.vo.price.PriceParkVo;
import com.obast.charer.qo.PriceParkQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则服务接口
 */
public interface IPriceParkManagerService {
    Paging<PriceParkVo> queryPageList(PageRequest<PriceParkQueryBo> pageRequest);


    List<PriceParkVo> queryList(PageRequest<PriceParkQueryBo> pageRequest);


    PriceParkVo queryDetail(String id);


    boolean add(PriceParkBo data);

    boolean update(PriceParkBo data);

    void updateStatus(PriceParkBo bo);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);


}
