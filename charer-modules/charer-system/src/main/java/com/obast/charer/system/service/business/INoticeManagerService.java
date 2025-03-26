package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.NoticeBo;
import com.obast.charer.system.dto.vo.notice.NoticeVo;
import com.obast.charer.qo.NoticeQueryBo;
import java.util.List;

public interface INoticeManagerService {
    Paging<NoticeVo> queryPageList(PageRequest<NoticeQueryBo> pageRequest);

    List<NoticeVo> queryList(PageRequest<NoticeQueryBo> pageRequest);

    NoticeVo queryDetail(String stationId);

    boolean add(NoticeBo data);

    boolean update(NoticeBo data);

    void publish(String id);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);


}
