package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.system.ISysPostData;
import com.obast.charer.system.dto.bo.SysPostBo;
import com.obast.charer.system.dto.vo.SysPostVo;
import com.obast.charer.model.system.SysPost;
import com.obast.charer.system.service.system.ISysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 岗位信息 服务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysPostServiceImpl implements ISysPostService {

    private final ISysPostData sysPostData;

    @Override
    public Paging<SysPostVo> selectPagePostList(PageRequest<SysPostBo> query) {
        return sysPostData.findAll(query.to(SysPost.class)).to(SysPostVo.class);
    }

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPostVo> selectPostList(SysPostBo post) {
        return MapstructUtils.convert(sysPostData.selectPostList(post.to(SysPost.class)),SysPostVo.class);
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPostVo> selectPostAll() {
        return MapstructUtils.convert(sysPostData.findAll(), SysPostVo.class);
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPostVo selectPostById(String postId) {
        return sysPostData.findById(postId).to(SysPostVo.class);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<String> selectPostListByUserId(String userId) {
        return sysPostData.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostNameUnique(SysPostBo post) {
        return sysPostData.checkPostNameUnique(post.to(SysPost.class));
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean checkPostCodeUnique(SysPostBo post) {
        return sysPostData.checkPostCodeUnique(post.to(SysPost.class));
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public long countUserPostById(String postId) {
        return 0;
    }

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public void deletePostById(String postId) {
        sysPostData.deleteById(postId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    @Override
    public void deletePostByIds(Collection<String> postIds) {
        sysPostData.deleteByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     *
     * @param bo 岗位信息
     * @return 结果
     */
    @Override
    public void insertPost(SysPostBo bo) {
        sysPostData.save(bo.to(SysPost.class));
    }

    /**
     * 修改保存岗位信息
     *
     * @param bo 岗位信息
     * @return 结果
     */
    @Override
    public void updatePost(SysPostBo bo) {
        sysPostData.save(bo.to(SysPost.class));
    }
}
