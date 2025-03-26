package com.obast.charer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合作商基类
 *
 * @author Michelle.Chung
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DealerModel extends AgentModel {

    /**
     * 合作商编号
     */
    private String dealerId;

}
