package com.obast.charer.system.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.CacheNames;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.oss.constant.OssConstant;
import com.obast.charer.common.redis.utils.CacheUtils;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.system.ISysOssConfigData;
import com.obast.charer.model.system.SysOssConfig;
import com.obast.charer.qo.SysOssConfigQueryBo;
import com.obast.charer.system.dto.bo.SysOssConfigBo;
import com.obast.charer.system.dto.vo.SysOssConfigVo;
import com.obast.charer.system.service.system.ISysOssConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 对象存储配置Service业务层处理
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class SysOssConfigServiceImpl implements ISysOssConfigService {

    private final ISysOssConfigData sysOssConfigData;

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    private void checkOssConfig() {
        //log.debug("检查 oss config 缓存");
        String configKey = RedisUtils.getCacheObject(OssConstant.DEFAULT_CONFIG_KEY);
        if(configKey==null){
            init();
        }
    }

    /**
     * 项目启动时，初始化参数到缓存，加载配置类
     */
    @Override
    public void init() {
//        List<SysOssConfig> list = sysOssConfigData.findAll();
//        List<SysOssConfig> notEmptyTenantIdList = new ArrayList<>(list);
//        Map<String, List<SysOssConfig>> map = StreamUtils.groupByKey(notEmptyTenantIdList, SysOssConfig::getTenantId);
//        try {
//            for (Map.Entry<String, List<SysOssConfig>> stringListEntry : map.entrySet()) {
//                TenantHelper.setDynamic(stringListEntry.getKey());
//                for (SysOssConfig config : stringListEntry.getValue()) {
//                    String configKey = config.getConfigKey();
//                    if (EnableStatusEnum.Enabled.equals(config.getStatus())) {
//                        RedisUtils.setCacheObject(OssConstant.DEFAULT_CONFIG_KEY, configKey);
//                    }
//                    CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(config));
//                }
//            }
//        } finally {
//            TenantHelper.clearDynamic();
//        }

        //todo
    }

    @Override
    public Paging<SysOssConfigVo> queryPageList(PageRequest<SysOssConfigQueryBo> pageRequest) {
        return sysOssConfigData.findPage(pageRequest).to(SysOssConfigVo.class);
    }


    @Override
    public SysOssConfigVo queryById(String ossConfigId) {
        return MapstructUtils.convert(sysOssConfigData.findById(ossConfigId), SysOssConfigVo.class);
    }


    @Override
    public Boolean insertByBo(SysOssConfigBo bo) {
        SysOssConfig config = MapstructUtils.convert(bo, SysOssConfig.class);
        validEntityBeforeSave(config);
        SysOssConfig save = sysOssConfigData.add(config);
        if (ObjectUtil.isNotNull(save)) {
            CacheUtils.put(CacheNames.SYS_OSS_CONFIG, config.getConfigKey(), JsonUtils.toJsonString(save));
        }
        return true;
    }

    @Override
    public Boolean updateByBo(SysOssConfigBo bo) {
        SysOssConfig config = MapstructUtils.convert(bo, SysOssConfig.class);
        validEntityBeforeSave(config);
        SysOssConfig savedConfig = sysOssConfigData.update(config);

        CacheUtils.put(CacheNames.SYS_OSS_CONFIG, savedConfig.getConfigKey(), JsonUtils.toJsonString(savedConfig));
        return true;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysOssConfig entity) {
        if (StringUtils.isNotEmpty(entity.getConfigKey())
                && !checkConfigKeyUnique(entity)) {
            throw new BizException("操作配置'" + entity.getConfigKey() + "'失败, 配置key已存在!");
        }
    }

    @Override
    public Boolean delete(String id) {
        SysOssConfig sysOssConfig = sysOssConfigData.findById(id);
        if(ObjectUtil.isNull(sysOssConfig)) {
            throw new BizException(ErrCode.OSS_CONFIG_NOT_FOUND);
        }
        if (CollUtil.contains(OssConstant.SYSTEM_DATA_IDS, id)) {
            throw new BizException(ErrCode.OSS_CONFIG_UNAUTHORIZED);
        }
        sysOssConfigData.deleteById(id);
        CacheUtils.evict(CacheNames.SYS_OSS_CONFIG, sysOssConfig.getConfigKey());
        return true;
    }

    /**
     * 判断configKey是否唯一
     */
    private boolean checkConfigKeyUnique(SysOssConfig sysOssConfig) {
        String ossConfigId = ObjectUtil.isNull(sysOssConfig.getId()) ? "-1" : sysOssConfig.getId();
        SysOssConfig q = new SysOssConfig();
        q.setConfigKey(sysOssConfig.getConfigKey());
        SysOssConfig info = sysOssConfigData.findOneByCondition(q);
        return !ObjectUtil.isNotNull(info) || Objects.equals(info.getId(), ossConfigId);
    }

    /**
     * 启用禁用状态
     */
    @Override
    public int updateOssConfigStatus(SysOssConfigBo bo) {
        SysOssConfig old = sysOssConfigData.findById(bo.getId());
        old.setStatus(bo.getStatus());
        sysOssConfigData.save(old);
        RedisUtils.deleteObject(OssConstant.DEFAULT_CONFIG_KEY);
        return 0;
    }

}

