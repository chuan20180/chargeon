package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.ProductQueryBo;
import com.obast.charer.system.dto.bo.ProductBo;
import com.obast.charer.system.dto.vo.product.ProductVo;

import java.util.List;

/**
 * 产品服务接口
 */
public interface IProductService {

    Paging<ProductVo> queryPageList(PageRequest<ProductQueryBo> pageRequest);

    List<ProductVo> queryList(PageRequest<ProductQueryBo> pageRequest);

    ProductVo queryDetail(String productId);

    ProductVo add(ProductBo data);

    boolean update(ProductBo productBo);

    void updateStatus(ProductBo bo);

    boolean delete(String id);
}
