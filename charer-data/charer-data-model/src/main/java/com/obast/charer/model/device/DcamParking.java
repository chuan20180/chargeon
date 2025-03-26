package com.obast.charer.model.device;

import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DcamParking extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String dcamId;

    private String parkingId;

    private String name;

    private String no;

    private String note;

}
