package com.obast.charer.model.sms;

import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;


import lombok.*;

import java.io.Serializable;

/**
 * @ Author：chuan
 * @ Date：2024-10-05-04:40
 * @ Version：1.0
 * @ Description：SmsConfig
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRecord extends TenantModel implements Id<String>, Serializable {

    private String id;

    public String mobile;

    public String message;

    public Integer result;

    public String identifier;

    public String type;

}
