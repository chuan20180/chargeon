package com.obast.charer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代理商基类
 *
 * @author Michelle.Chung
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AgentModel extends TenantModel {

    /**
     * 代理商编号
     */
    private String agentId;

}
