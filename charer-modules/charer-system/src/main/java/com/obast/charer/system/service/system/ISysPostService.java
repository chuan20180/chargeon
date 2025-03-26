package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysPostBo;
import com.obast.charer.system.dto.vo.SysPostVo;

import java.util.Collection;
import java.util.List;

/**
 * 岗位信息 服务层
 *
 * @author Lion Li
 */
public interface ISysPostService {


    Paging<SysPostVo> selectPagePostList(PageRequest<SysPostBo> query);

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位列表
     */
    List<SysPostVo> selectPostList(SysPostBo post);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SysPostVo> selectPostAll();

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    SysPostVo selectPostById(String postId);

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    List<String> selectPostListByUserId(String userId);

    /**
     * 校验岗位名称
     *
     * @param post 岗位信息
     * @return 结果
     */
    boolean checkPostNameUnique(SysPostBo post);

    /**
     * 校验岗位编码
     *
     * @param post 岗位信息
     * @return 结果
     */
    boolean checkPostCodeUnique(SysPostBo post);

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    long countUserPostById(String postId);

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    void deletePostById(String postId);

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    void deletePostByIds(Collection<String> postIds);

    /**
     * 新增保存岗位信息
     *
     * @param bo 岗位信息
     * @return 结果
     */
    void insertPost(SysPostBo bo);

    /**
     * 修改保存岗位信息
     *
     * @param bo 岗位信息
     * @return 结果
     */
    void updatePost(SysPostBo bo);
}
