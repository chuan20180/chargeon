package com.obast.charer.system.service.platform.impl;

import cn.hutool.core.lang.UUID;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.model.product.Product;
import com.obast.charer.qo.ProductQueryBo;
import com.obast.charer.system.dto.bo.ProductBo;
import com.obast.charer.system.dto.vo.product.ProductVo;
import com.obast.charer.system.service.platform.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductData productData;

    @Override
    public Paging<ProductVo> queryPageList(PageRequest<ProductQueryBo> pageRequest) {
        return productData.findPage(pageRequest).to(ProductVo.class);
    }

    @Override
    public List<ProductVo> queryList(PageRequest<ProductQueryBo> pageRequest) {
        return MapstructUtils.convert(productData.findList(pageRequest.getData()), ProductVo.class);
    }

    @Override
    public ProductVo queryDetail(String id) {
        return productData.findById(id).to(ProductVo.class);
    }

    @Override
    public ProductVo add(ProductBo data) {
        Product product = data.to(Product.class);
        String secret = UUID.randomUUID().toString(true);
        product.setProductSecret(secret);
        String productKey = data.getProductKey();
        Product oldProduct = productData.findByProductKey(productKey);
        if (oldProduct != null) {
            throw new BizException(ErrCode.PRODUCT_KEY_EXIST);
        }
        productData.save(product);
        return MapstructUtils.convert(product, ProductVo.class);
    }

    @Override
    public boolean update(ProductBo productBo) {
        Product product = productBo.to(Product.class);
        productData.save(product);
        return true;
    }

    @Override
    public void updateStatus(ProductBo bo) {
        Product product = productData.findById(bo.getId());
        product.setStatus(bo.getStatus());
        productData.save(product);
    }


    @Override
    public boolean delete(String id) {
        Product product = productData.findById(id);
        if (Objects.isNull(product)) {
            throw new BizException(ErrCode.PRODUCT_NOT_FOUND);
        }
        productData.deleteById(product.getId());
        return true;
    }
}
