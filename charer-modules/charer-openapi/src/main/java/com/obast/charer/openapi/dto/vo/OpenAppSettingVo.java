package com.obast.charer.openapi.dto.vo;

import com.obast.charer.common.properties.OpenapiProperties;
import com.obast.charer.model.map.MapConfig;
import com.obast.charer.model.system.SysApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAppSettingVo {

    private SysApp app;

    private MapConfig map;

    private Map<String, Object> config;

    private OpenapiProperties properties;

    private OpenNotifyConfigVo  notify;

}
