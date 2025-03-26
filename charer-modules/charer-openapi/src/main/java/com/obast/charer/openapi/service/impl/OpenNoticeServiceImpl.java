package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.INoticeData;
import com.obast.charer.model.system.Notice;
import com.obast.charer.openapi.dto.vo.OpenNoticeVo;
import com.obast.charer.openapi.service.IOpenNoticeService;
import com.obast.charer.qo.NoticeQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OpenNoticeServiceImpl implements IOpenNoticeService {

    @Autowired
    private INoticeData noticeData;

    @Override
    public Paging<OpenNoticeVo> queryPage(PageRequest<NoticeQueryBo> pageRequest) {
        Paging<Notice> pageList = noticeData.findPage(pageRequest);
        Paging<OpenNoticeVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Notice Notice: pageList.getRows()) {
            newPageList.getRows().add(fillData(Notice));
        }
        return newPageList;
    }

    @Override
    public OpenNoticeVo queryDetail(String NoticeId) {
        Notice charger = noticeData.findById(NoticeId);
        return fillData(charger);
    }

    private OpenNoticeVo fillData(Notice Notice) {
        return MapstructUtils.convert(Notice, OpenNoticeVo.class);
    }
}