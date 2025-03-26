package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.message.event.NoticeEvent;
import com.obast.charer.data.business.INoticeData;
import com.obast.charer.system.dto.bo.NoticeBo;
import com.obast.charer.system.dto.vo.notice.NoticeVo;
import com.obast.charer.system.service.business.INoticeManagerService;
import com.obast.charer.enums.NoticeStateEnum;
import com.obast.charer.enums.NoticeTypeEnum;
import com.obast.charer.model.system.Notice;
import com.obast.charer.qo.NoticeQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeManagerServiceImpl implements INoticeManagerService {

    @Autowired
    private INoticeData noticeData;

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Paging<NoticeVo> queryPageList(PageRequest<NoticeQueryBo> pageRequest) {
        Paging<Notice> pageList = noticeData.findPage(pageRequest);
        Paging<NoticeVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Notice notice: pageList.getRows()) {
            newPageList.getRows().add(fillData(notice));
        }
        return newPageList;
    }

    @Override
    public List<NoticeVo> queryList(PageRequest<NoticeQueryBo> pageRequest) {
        List<Notice> list = noticeData.findList(pageRequest.getData());
        List<NoticeVo> newList = new ArrayList<>();
        for(Notice notice: list) {
            newList.add(fillData(notice));
        }
        return newList;
    }

    @Override
    public NoticeVo queryDetail(String noticeId) {
        return fillData(noticeData.findById(noticeId));
    }

    @Override
    public boolean add(NoticeBo bo) {
        Notice di = bo.to(Notice.class);
        return noticeData.add(di) != null;
    }

    @Override
    public boolean update(NoticeBo bo) {
        Notice di = bo.to(Notice.class);
        return noticeData.update(di) != null;
    }


    @Override
    public void publish(String noticeId) {
        Notice notice = noticeData.findById(noticeId);
        notice.setState(NoticeStateEnum.Published);
        noticeData.save(notice);
        if(notice.getType().equals(NoticeTypeEnum.Notice)) {
            applicationEventPublisher.publishEvent(new NoticeEvent(notice));
        }
    }

    @Override
    public boolean delete(String noticeId) {
        noticeId = queryDetail(noticeId).getId();

        noticeData.deleteById(noticeId);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String noticeId: ids) {
            noticeData.deleteById(noticeId);
        }
        return true;
    }

    private NoticeVo fillData(Notice notice) {
        return MapstructUtils.convert(notice, NoticeVo.class);
    }
}