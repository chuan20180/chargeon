package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.qo.SysOssQueryBo;
import com.obast.charer.system.dto.bo.SysOssBo;
import com.obast.charer.system.dto.vo.SysOssVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 文件上传 服务层
 *
 * @author Lion Li
 */
public interface ISysOssService {

    Paging<SysOssVo> queryPageList(PageRequest<SysOssQueryBo> query);

    List<SysOssVo> listByIds(Collection<String> ossIds);

    SysOssVo getById(String ossId);

    SysOssVo upload(MultipartFile file);

    void download(String ossId) throws IOException;

    void deleteWithValidByIds(Collection<String> ids, Boolean isValid);

}
