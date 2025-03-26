package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.Notice;
import com.obast.charer.qo.NoticeQueryBo;

/**
 * 通知数据接口
 *
 * @author sjg
 */
public interface INoticeData extends ICommonData<Notice, String>, IJPACommonData<Notice, NoticeQueryBo, String> {

    Notice add(Notice notice);

    Notice update(Notice notice);
}
