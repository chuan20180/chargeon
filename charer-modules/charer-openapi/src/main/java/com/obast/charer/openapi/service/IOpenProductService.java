package com.obast.charer.openapi.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.openapi.dto.vo.OpenAdsVo;
import com.obast.charer.openapi.dto.vo.OpenProductVo;
import com.obast.charer.qo.AdsQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI广告服务接口
 */
public interface IOpenProductService {

    OpenProductVo findByProductKey(String productKey);


}
