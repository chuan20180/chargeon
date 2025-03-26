package com.obast.charer.model.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleLog {

    public static final String STATE_MATCHED_LISTENER = "matched_listener";
    public static final String STATE_MATCHED_FILTER = "matched_filter";
    public static final String STATE_UNMATCHED_FILTER = "unmatched_filter";
    public static final String STATE_EXECUTED_ACTION = "executed_action";

    private String id;

    private String ruleId;

    private String state;

    private String content;

    private Boolean success;

    private Long logAt;
}
