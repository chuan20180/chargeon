package com.obast.charer.system.service.system.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.oss.core.OssClient;
import com.obast.charer.common.oss.entity.UploadResult;
import com.obast.charer.common.oss.enumd.AccessPolicyType;
import com.obast.charer.common.oss.exception.OssException;
import com.obast.charer.common.oss.factory.OssFactory;
import com.obast.charer.common.service.OssService;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.system.ISysOssData;
import com.obast.charer.model.system.SysOss;
import com.obast.charer.qo.SysOssQueryBo;
import com.obast.charer.system.dto.bo.SysOssBo;
import com.obast.charer.system.dto.vo.SysOssVo;
import com.obast.charer.system.service.system.ISysOssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 文件上传 服务层实现
 *
 * @author Lion Li
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysOssServiceImpl implements ISysOssService, OssService {

    private final ISysOssData sysOssData;

    @Override
    public Paging<SysOssVo> queryPageList(PageRequest<SysOssQueryBo> query) {
        return sysOssData.findPage(query).to(SysOssVo.class);
    }

    @Override
    public List<SysOssVo> listByIds(Collection<String> ossIds) {
        List<SysOssVo> list = new ArrayList<>();
        for (String id : ossIds) {
            SysOss oss = sysOssData.findById(id);
            if (ObjectUtil.isNotNull(oss)) {
                SysOssVo vo = MapstructUtils.convert(oss, SysOssVo.class);
                list.add(this.matchingUrl(vo));
            }
        }
        return list;
    }

    @Override
    public String selectUrlByIds(String ossIds) {
        List<String> list = new ArrayList<>();
        for (String id : StringUtils.splitTo(ossIds, Convert::toStr)) {
            SysOssVo vo = SpringUtils.getAopProxy(this).getById(id);
            if (ObjectUtil.isNotNull(vo)) {
                list.add(this.matchingUrl(vo).getUrl());
            }
        }
        return String.join(StringUtils.SEPARATOR, list);
    }

    @Override
    public SysOssVo getById(String ossId) {
        return sysOssData.findById(ossId).to(SysOssVo.class);
    }

    @Override
    public void download(String ossId) {
    }

    @Override
    public SysOssVo upload(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new BizException("文件名为空，获取失败");
        }
        String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
        OssClient storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());

            log.debug("上传结果: {}", uploadResult);
        } catch (IOException e) {
            throw new BizException(e.getMessage());
        }
        // 保存文件信息
        SysOss oss = new SysOss();
        oss.setUrl(uploadResult.getUrl());
        oss.setFileSuffix(suffix);
        oss.setFileName(uploadResult.getFilename());
        oss.setOriginalName(originalFileName);
        oss.setService(storage.getConfigKey());
        oss = sysOssData.save(oss);
        SysOssVo sysOssVo = MapstructUtils.convert(oss, SysOssVo.class);
        return this.matchingUrl(sysOssVo);
    }

    @Override
    public void deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if (Boolean.TRUE.equals(isValid)) {
            // 做一些业务上的校验,判断是否需要校验
        }
        List<SysOss> list = sysOssData.findByIds(ids);
        for (SysOss sysOss : list) {
            OssClient storage = OssFactory.instance(sysOss.getService());
            storage.delete(sysOss.getUrl());
        }
        sysOssData.deleteByIds(ids);
    }

    /**
     * 匹配Url
     *
     * @param oss OSS对象
     * @return oss 匹配Url的OSS对象
     */
    private SysOssVo matchingUrl(SysOssVo oss) {
        try {
            OssClient storage = OssFactory.instance(oss.getService());
            // 仅修改桶类型为 private 的URL，临时URL时长为120s
            if (AccessPolicyType.PRIVATE == storage.getAccessPolicy()) {
                oss.setUrl(storage.getPrivateUrl(oss.getFileName(), 120));
            }
            return oss;
        } catch (OssException e) {
            log.error("matchingUrl error", e);
            return new SysOssVo();
        }
    }
}
