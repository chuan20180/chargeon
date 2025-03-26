package com.obast.charer.data.service;


import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.Constants;
import com.obast.charer.data.cache.ProductCacheEvict;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.model.product.Product;
import com.obast.charer.qo.ProductQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Qualifier("productDataCache")
public class ProductDataCache implements IProductData {

    @Autowired
    private IProductData productData;
    @Autowired
    private ProductCacheEvict productCacheEvict;

    @Override
    public List<Product> findByCategory(String category) {
        return productData.findByCategory(category);
    }

    @Override
    @Cacheable(value = Constants.CACHE_PRODUCT, key = "#root.method.name+#productKey", unless = "#result == null")
    public Product findByProductKey(String productKey) {
        return productData.findByProductKey(productKey);
    }


//    @Override
//    public List<Product> findByUid(String uid) {
//        return productData.findByUid(uid);
//    }

//    @Override
//    public Paging<Product> findByUid(String uid, int page, int size) {
//        return productData.findByUid(uid, page, size);
//    }

//    @Override
//    public long countByUid(String uid) {
//        return productData.countByUid(uid);
//    }

    @Override
    @Cacheable(value = Constants.CACHE_PRODUCT, key = "#root.method.name+#s", unless = "#result == null")
    @Deprecated
    public Product findById(String s) {
        return productData.findById(s);
    }

    @Override
    public List<Product> findByIds(Collection<String> id) {
        return null;
    }

    @Override
    public Product save(Product data) {
        Product p = productData.save(data);
        //清除缓存
        productCacheEvict.findById(p.getId());
        productCacheEvict.findByProductKey(p.getProductKey());
        return p;
    }

    @Override
    public void batchSave(List<Product> data) {

    }

    @Override
    public void deleteById(String s) {
        Product product = findById(s);

        productData.deleteById(s);
        //清除缓存
        productCacheEvict.findById(s);
    }

    @Override
    public void deleteByIds(Collection<String> ids) {

    }

    @Override
    public long count() {
        return productData.count();
    }

    @Override
    public List<Product> findAll() {
        return productData.findAll();
    }

    @Override
    public Paging<Product> findAll(PageRequest<Product> pageRequest) {
        return productData.findAll(pageRequest);
    }

    @Override
    public List<Product> findAllByCondition(Product data) {
        return null;
    }

    @Override
    public Product findOneByCondition(Product data) {
        return null;
    }

    @Override
    public Paging<Product> findPage(PageRequest<ProductQueryBo> pageRequest) {
        return null;
    }

    @Override
    public List<Product> findList(ProductQueryBo queryBo) {
        return null;
    }
}
