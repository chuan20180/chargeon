package com.obast.charer.data.platform;

import com.obast.charer.qo.ProductQueryBo;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.product.Product;

import java.util.List;

/**
 * 产品接口
 */
public interface IProductData extends ICommonData<Product, String>, IJPACommonData<Product, ProductQueryBo, String> {

    /**
     * 按品类取产品列表
     */
    List<Product> findByCategory(String category);

    Product findByProductKey(String productKey);

}
