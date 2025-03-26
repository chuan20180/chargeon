package com.obast.charer.openapi.service;

import com.obast.charer.openapi.dto.vo.OpenCouponCodeVo;
import com.obast.charer.qo.CouponCodeQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI优惠码服务接口
 */
public interface IOpenCouponCodeService {
    Paging<OpenCouponCodeVo> queryPage(PageRequest<CouponCodeQueryBo> bo);

    OpenCouponCodeVo queryDetail(String id);

    List<OpenCouponCodeVo> queryByCustomerId(String customerId);

}
