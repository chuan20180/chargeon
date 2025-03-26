package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.CouponCodeBo;
import com.obast.charer.system.dto.vo.coupon.CouponCodeVo;
import com.obast.charer.qo.CouponCodeQueryBo;
import com.obast.charer.model.coupon.CouponCode;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：优惠券服务接口
 */
public interface ICouponCodeManagerService {
    Paging<CouponCodeVo> queryPageList(PageRequest<CouponCodeQueryBo> pageRequest);

    List<CouponCodeVo> queryList(PageRequest<CouponCodeQueryBo> pageRequest);

    CouponCodeVo queryDetail(String id);

    boolean addCouponCode(CouponCodeBo data);

    boolean removeCouponCode(String id);

    boolean batchRemoveCouponCode(List<String> ids);

    CouponCode getDetail(String id);

}
