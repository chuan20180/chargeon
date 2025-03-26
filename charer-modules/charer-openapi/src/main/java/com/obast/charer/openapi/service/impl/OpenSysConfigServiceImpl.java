package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.data.system.ISysConfigData;
import com.obast.charer.model.system.SysConfig;
import com.obast.charer.openapi.service.IOpenSysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class OpenSysConfigServiceImpl implements IOpenSysConfigService {

    @Autowired
    private ISysConfigData sysConfigData;

    @Override
    public Map<String, Object> querySysConfigByTenantId(String tenantId) {
        List<SysConfig> sysConfigs = sysConfigData.findAllByTenantId(tenantId);
        Map<String, Object> configs = new HashMap<>();
        for(SysConfig sysConfig: sysConfigs) {
            configs.put(sysConfig.getConfigKey(), sysConfig.getConfigValue());
        }
        return configs;
    }

    @Override
    public Object selectByConfigKey(String key) {
        SysConfig sysConfig = sysConfigData.findByConfigKey(key);

        switch (sysConfig.getDataType()) {
            case Text:
                return sysConfig.getConfigValue();
            case Int:
                return Integer.parseInt(sysConfig.getConfigValue());
            case Float:
                return Float.valueOf(sysConfig.getConfigValue());
            case Decimal:
                return new BigDecimal(sysConfig.getConfigValue());
            case I18nText:
            case I18nTextarea:
                try {
                    I18nField i18nField = JsonUtils.parseObject(sysConfig.getConfigValue(), I18nField.class);
                    Locale locale = LocaleContextHolder.getLocale();
                    String language = String.format("%s_%s", locale.getLanguage(), locale.getCountry());

                    if(i18nField == null) {
                        throw new BizException(ErrCode.SYSTEM_EXCEPTION);
                    }
                    Field[] fields = i18nField.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if(field.getName().equals(language)) {
                            return field.get(i18nField);
                        }
                    }
                } catch (Exception e) {
                    throw new BizException(ErrCode.SYSTEM_EXCEPTION, e.getLocalizedMessage());
                }
                break;
            default:
                break;

        }

        return null;
    }
}
