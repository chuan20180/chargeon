package com.obast.charer.openapi.service.impl;

import com.obast.charer.data.platform.IProductData;
import com.obast.charer.openapi.dto.vo.OpenProductVo;
import com.obast.charer.openapi.service.IOpenProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenProductServiceImpl implements IOpenProductService {

    @Autowired
    private IProductData productData;


    @Override
    public OpenProductVo findByProductKey(String productKey) {
        return productData.findByProductKey(productKey).to(OpenProductVo.class);
    }
}
