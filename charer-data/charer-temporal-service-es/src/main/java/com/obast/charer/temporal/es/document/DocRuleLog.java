package com.obast.charer.temporal.es.document;

import com.obast.charer.model.rule.RuleLog;
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
@Document(indexName = "rule_log")
@AutoMapper(target = RuleLog.class)
public class DocRuleLog {
    @Id
    private String id;

    private String ruleId;

    private String state;

    private String content;

    private Boolean success;

    @Field(type = FieldType.Date)
    private Long logAt;
}
