package com.obast.charer.data.business;

import com.obast.charer.common.api.Paging;
import com.obast.charer.data.IOwnedData;
import com.obast.charer.model.rule.RuleInfo;

import java.util.List;

public interface IRuleInfoData extends IOwnedData<RuleInfo, String> {

    List<RuleInfo> findByUidAndType(String uid, String type);

    Paging<RuleInfo> findByUidAndType(String uid, String type, int page, int size);

    Paging<RuleInfo> findByType(String type, int page, int size);

}
