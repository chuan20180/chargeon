package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenProtocolVo;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI广告服务接口
 */
public interface IOpenProtocolService {

    OpenProtocolVo findByProtocolKey(String protocolKey);


}
