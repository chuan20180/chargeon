package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysAgentStationDealerQueryBo extends BaseDto {

    private String agentId;

    private String agentStationId;

    private String dealerId;

    private EnableStatusEnum status;
}
