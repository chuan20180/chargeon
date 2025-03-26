package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.model.system.SysUserPost;

/**
 * 用户岗位数据接口
 *
 * @author sjg
 */
public interface ISysUserPostData extends ICommonData<SysUserPost, String> {

    /**
     * 按用户id删除数据
     *
     * @param userId 用户id
     * @return 数量
     */
    int deleteByUserId(String userId);
}
