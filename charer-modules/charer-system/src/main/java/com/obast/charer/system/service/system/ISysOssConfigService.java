package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.SysOssConfigQueryBo;
import com.obast.charer.system.dto.bo.SysOssConfigBo;
import com.obast.charer.system.dto.vo.SysOssConfigVo;

/**
 * @ Author：chuan
 * @ Date：2024-09-27-17:19
 * @ Version：1.0
 * @ Description：对象存储配置Service接口
 */
public interface ISysOssConfigService {


    /**
     * 初始化OSS配置
     */
    void init();


    /**
     * 查询单个
     */
    SysOssConfigVo queryById(String ossConfigId);

    /**
     * 查询列表
     */
    Paging<SysOssConfigVo> queryPageList(PageRequest<SysOssConfigQueryBo> query);


    /**
     * 根据新增业务对象插入对象存储配置
     */
    Boolean insertByBo(SysOssConfigBo bo);

    /**
     * 根据编辑业务对象修改对象存储配置
     */
    Boolean updateByBo(SysOssConfigBo bo);

    /**
     * 校验并删除数据
     */
    Boolean delete(String id);

    /**
     * 启用停用状态
     */
    int updateOssConfigStatus(SysOssConfigBo bo);

}
