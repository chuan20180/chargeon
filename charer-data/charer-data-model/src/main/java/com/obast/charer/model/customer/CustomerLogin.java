package com.obast.charer.model.customer;

import com.obast.charer.common.enums.LoginTypeEnum;
import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLogin extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String customerId;

    private LoginTypeEnum loginType;

    private String dn;

    private String os;

    private PlatformTypeEnum platform;

    private String device;

    private String version;

    private String language;

    private String loginIp;

    private Date loginDate;
}
