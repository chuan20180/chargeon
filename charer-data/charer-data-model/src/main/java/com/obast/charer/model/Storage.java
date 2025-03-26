package com.obast.charer.model;

import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String productKey;

    private String name;

    private String dn;

    private String note;

}
