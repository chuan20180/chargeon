package com.obast.charer.system.dto.vo;

import com.obast.charer.common.properties.CharerProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 租户列表
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppSettingVo {

    private CharerProperties charer;

    private Map<String, Object> config;



}
