package com.obast.charer.temporal.es.document;

import com.obast.charer.common.thing.ThingModelMessage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "thing_model_message")
@AutoMapper(target = ThingModelMessage.class)
public class DocThingModelMessage {

    @Id
    private String id;

    private String mid;

    private String deviceId;

    private String productKey;

    private String deviceName;

    private String type;

    private String invoke;

    private String directive;

    private String identifier;

    private int code;

    private Object data;

    private String pack;

    @Field(type = FieldType.Date)
    private Long occurred;

    @Field(type = FieldType.Date)
    private Long time;

}
