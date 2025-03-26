package com.obast.charer.model.system;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysDealer extends AgentModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private String contactPerson;

    private String contactMobile;

    private List<String> menuIds;

    private EnableStatusEnum status;

    private String note;
}
