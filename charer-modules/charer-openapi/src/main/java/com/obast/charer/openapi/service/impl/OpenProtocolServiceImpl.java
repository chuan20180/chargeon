package com.obast.charer.openapi.service.impl;

import com.obast.charer.data.platform.IProductData;
import com.obast.charer.data.platform.IProtocolData;
import com.obast.charer.openapi.dto.vo.OpenProductVo;
import com.obast.charer.openapi.dto.vo.OpenProtocolVo;
import com.obast.charer.openapi.service.IOpenProductService;
import com.obast.charer.openapi.service.IOpenProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenProtocolServiceImpl implements IOpenProtocolService {

    @Autowired
    private IProtocolData protocolData;


    @Override
    public OpenProtocolVo findByProtocolKey(String protocolKey) {
        return protocolData.findByProtocolKey(protocolKey).to(OpenProtocolVo.class);
    }
}
