package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.utils.DateUtils;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.CacheNames;
import com.obast.charer.common.constant.UserConstants;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.redis.utils.CacheUtils;
import com.obast.charer.common.service.ConfigService;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.data.ICommonData;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.system.dto.bo.SysConfigBo;
import com.obast.charer.system.dto.vo.SysConfigVo;
import com.obast.charer.model.system.SysConfig;

import com.obast.charer.system.service.system.ISysConfigService;
import com.obast.charer.qo.SysConfigQueryBo;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ZipUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.obast.charer.data.business.*;
import com.obast.charer.data.system.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * 参数配置 服务层实现
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysConfigServiceImpl implements ISysConfigService, ConfigService {

    @Autowired
    private ISysConfigData sysConfigData;

    @Override
    public Paging<SysConfigVo> queryPage(PageRequest<SysConfigQueryBo> pageRequest) {
        return sysConfigData.findPage(pageRequest).to(SysConfigVo.class);
    }

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public SysConfigVo selectConfigById(String configId) {
        return sysConfigData.findById(configId).to(SysConfigVo.class);
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfig sysConfig = sysConfigData.findByConfigKey(configKey);
        if (ObjectUtil.isNotNull(sysConfig)) {
            return sysConfig.getConfigValue();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取注册开关
     *
     * @param tenantId 租户id
     * @return true开启，false关闭
     */
    @Override
    public boolean selectRegisterEnabled(String tenantId) {
        SysConfig query = new SysConfig();
        query.setConfigKey("sys.account.registerUser");
        SysConfig retConfig = sysConfigData.findOneByCondition(query);

        if (ObjectUtil.isNull(retConfig)) {
            return false;
        }
        return Convert.toBool(retConfig.getConfigValue());
    }

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfigVo> selectConfigList(SysConfigBo config) {

        List<SysConfig> allByCondition = sysConfigData.findAllByCondition(MapstructUtils.convert(config, SysConfig.class));
        return MapstructUtils.convert(allByCondition, SysConfigVo.class);
    }

    /**
     * 新增参数配置
     *
     * @param bo 参数配置信息
     * @return 结果
     */
    @Override
    public String insertConfig(SysConfigBo bo) {
        SysConfig to = bo.to(SysConfig.class);
        sysConfigData.save(to);
        return String.valueOf(to.getId());
    }

    /**
     * 修改参数配置
     *
     * @param bo 参数配置信息
     * @return 结果
     */
//    @CachePut(cacheNames = CacheNames.SYS_CONFIG, key = "#bo.configKey")
    @Override
    public String updateConfig(SysConfigBo bo) {
        SysConfig config = MapstructUtils.convert(bo, SysConfig.class);
        if(config == null) {
            throw new BizException(ErrCode.SYSTEM_EXCEPTION);
        }

        sysConfigData.save(config);
        return config.getConfigValue();
    }

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
    @Override
    public void deleteConfigByIds(List<String> configIds) {
        for (String configId : configIds) {
            SysConfig old = sysConfigData.findById(configId);
            if (StringUtils.equals(UserConstants.YES, old.getConfigType())) {
                throw new BizException(String.format("内置参数【%1$s】不能删除 ", old.getConfigKey()));
            }
            CacheUtils.evict(CacheNames.SYS_CONFIG, old.getConfigKey());
        }
        sysConfigData.deleteByIds(configIds);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        CacheUtils.clear(CacheNames.SYS_CONFIG);
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public boolean checkConfigKeyUnique(SysConfigBo config) {
        String configId = ObjectUtil.isNull(config.getId()) ? "-1" : config.getId();
        SysConfig old = sysConfigData.findByConfigKey(config.getConfigKey());
        return ObjectUtil.isNull(old) || Objects.equals(old.getId(), configId);
    }

    /**
     * 根据参数 key 获取参数值
     *
     * @param configKey 参数 key
     * @return 参数值
     */
    @Override
    public String getConfigValue(String configKey) {
        return SpringUtils.getAopProxy(this).selectConfigByKey(configKey);
    }

    @Override
    public File backupSysData() {
        File fileDir = new File("./data/backup/" + DateUtils.dateTimeNow());
        if (!fileDir.exists()) {
            FileUtil.mkdir(fileDir);
        }
        writeData(fileDir, "category", SpringUtils.getBean(ICategoryData.class));
        writeData(fileDir, "channelConfig", SpringUtils.getBean(IChannelConfigData.class));
        writeData(fileDir, "notifyMessage", SpringUtils.getBean(INotifyMessageData.class));
        writeData(fileDir, "product", SpringUtils.getBean(IProductData.class));
        writeData(fileDir, "ruleInfo", SpringUtils.getBean(IRuleInfoData.class));
        writeData(fileDir, "sys_app", SpringUtils.getBean(ISysAppData.class));
        writeData(fileDir, "sys_config", SpringUtils.getBean(ISysConfigData.class));
        writeData(fileDir, "sys_dept", SpringUtils.getBean(ISysDeptData.class));
        writeData(fileDir, "sys_dict_data", SpringUtils.getBean(ISysDictData.class));
        writeData(fileDir, "sys_dict_type", SpringUtils.getBean(ISysDictTypeData.class));
        writeData(fileDir, "sys_logininfor", SpringUtils.getBean(ISysLoginInfoData.class));
        writeData(fileDir, "sys_menu", SpringUtils.getBean(ISysMenuData.class));
        writeData(fileDir, "sys_notice", SpringUtils.getBean(INoticeData.class));
        writeData(fileDir, "sys_oper_log", SpringUtils.getBean(ISysOperLogData.class));
        writeData(fileDir, "sys_oss", SpringUtils.getBean(ISysOssData.class));
        writeData(fileDir, "sys_oss_config", SpringUtils.getBean(ISysOssConfigData.class));
        writeData(fileDir, "sys_post", SpringUtils.getBean(ISysPostData.class));
        writeData(fileDir, "sys_role", SpringUtils.getBean(ISysRoleData.class));

        writeData(fileDir, "sys_role_menu", SpringUtils.getBean(ISysRoleMenuData.class));
        writeData(fileDir, "sys_user", SpringUtils.getBean(ISysUserData.class));
        writeData(fileDir, "sys_user_post", SpringUtils.getBean(ISysUserPostData.class));
        writeData(fileDir, "sys_user_role", SpringUtils.getBean(ISysUserRoleData.class));
        writeData(fileDir, "taskInfo", SpringUtils.getBean(ITaskInfoData.class));
        writeData(fileDir, "pluginInfo", SpringUtils.getBean(IPluginInfoData.class));
        String zipPath = fileDir.getAbsolutePath() + ".zip";
        ZipUtil.zip(fileDir.getAbsolutePath(), zipPath);
        return new File(zipPath);
    }

    @SneakyThrows
    private void writeData(File dir, String name, ICommonData<?,?> data) {
        Path path = Paths.get(dir.getAbsolutePath(), name + ".json");
        ObjectMapper mapper=new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String formattedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.findAll());
        FileUtil.writeString(formattedJson, path.toFile(), StandardCharsets.UTF_8);
    }
}
