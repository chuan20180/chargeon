package com.obast.charer.model.map;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

/**
 * @ Author：chuan
 * @ Date：2024-10-05-04:40
 * @ Version：1.0
 * @ Description：MapConfig
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapConfig extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String name;

    public String identifier;

    private String properties;

    private EnableStatusEnum status;
}
