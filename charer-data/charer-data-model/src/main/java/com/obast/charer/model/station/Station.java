package com.obast.charer.model.station;

import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnOffEnum;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station extends AgentModel implements Id<String>, Serializable {

    private String id;

    private I18nField name;

    private I18nField address;

    private String longitude;

    private String latitude;

    private EnableStatusEnum status;

    private I18nField description;

    private String img;

    private String note;

    private I18nField tags;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Locate {

        private String longitude;

        private String latitude;

    }
}
