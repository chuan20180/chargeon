package com.obast.charer.system.dto;

import com.obast.charer.system.dto.vo.tenant.SysTenantVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 租户列表
 *
 * @author Lion Li
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AutoMapper(target = SysTenantVo.class)
public class TenantListVo {

    private String tenantId;

    private String companyName;

    private String domain;

    private String avatar;

    private String displayName;

}
