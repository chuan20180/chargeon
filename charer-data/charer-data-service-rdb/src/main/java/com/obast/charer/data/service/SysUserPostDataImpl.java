package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.SysUserPostRepository;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.model.TbSysUserPost;
import com.obast.charer.data.system.ISysUserPostData;
import com.obast.charer.model.system.SysUserPost;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：tfd
 * @Date：2023/5/30 17:04
 */

@Primary
@Service
@RequiredArgsConstructor
public class SysUserPostDataImpl implements ISysUserPostData, IJPACommData<SysUserPost, String> {


    private final SysUserPostRepository sysUserPostRepository;

    @Override
    public int deleteByUserId(String userId) {
        return sysUserPostRepository.deleteAllByUserId(userId);
    }

    @Override
    public JpaRepository getBaseRepository() {
        return sysUserPostRepository;
    }

    @Override
    public Class getJpaRepositoryClass() {
        return TbSysUserPost.class;
    }

    @Override
    public Class getTClass() {
        return SysUserPost.class;
    }

    @Override
    public void batchSave(List<SysUserPost> data) {
        sysUserPostRepository.saveAll(MapstructUtils.convert(data, TbSysUserPost.class));
    }
}
