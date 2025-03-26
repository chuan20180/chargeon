package com.obast.charer.system.service.business;


import com.obast.charer.qo.PriceQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.PriceBo;
import com.obast.charer.system.dto.vo.price.PriceVo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则服务接口
 */
public interface IPriceManagerService {
    Paging<PriceVo> queryPageList(PageRequest<PriceQueryBo> pageRequest);


    List<PriceVo> queryList(PageRequest<PriceQueryBo> pageRequest);


    PriceVo queryDetail(String priceId);


    boolean addPrice(PriceBo data);

    boolean updatePrice(PriceBo data);

    boolean issuePrice(String data);

    void updateStatus(PriceBo bo);

    boolean deletePrice(String id);

    boolean batchDeletePrice(List<String> ids);


}
