package com.obast.charer.openapi.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.openapi.dto.vo.OpenNoticeVo;
import com.obast.charer.qo.NoticeQueryBo;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：OPENAPI充电桩服务接口
 */
public interface IOpenNoticeService {
    Paging<OpenNoticeVo> queryPage(PageRequest<NoticeQueryBo> bo);

    OpenNoticeVo queryDetail(String id);
}
