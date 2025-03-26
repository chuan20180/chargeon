package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.model.system.SysPost;

import java.util.List;

/**
 * 操作日志数据接口
 *
 * @author sjg
 */
public interface ISysPostData extends ICommonData<SysPost, String> {
    List<String> selectPostListByUserId(String userId);

    List<SysPost> selectPostList(SysPost post);

    boolean checkPostNameUnique(SysPost post);

    boolean checkPostCodeUnique(SysPost post);
}
