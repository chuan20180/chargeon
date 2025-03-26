package com.obast.charer.temporal;

import com.obast.charer.common.api.Paging;
import com.obast.charer.model.rule.RuleLog;

public interface IRuleLogData {

    void deleteByRuleId(String ruleId);

    Paging<RuleLog> findByRuleId(String ruleId, int page, int size);

    void add(RuleLog log);

}
