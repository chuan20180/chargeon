package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.enums.NotifyIdentifierEnum;
import com.obast.charer.model.map.MapConfig;
import com.obast.charer.model.notify.NotifyConfig;
import com.obast.charer.qo.NotifyConfigQueryBo;

import java.util.List;

/**
 * author: 石恒
 * date: 2023-05-11 17:15
 * description:
 **/
public interface INotifyConfigData extends ICommonData<NotifyConfig, String>, IJPACommonData<NotifyConfig, NotifyConfigQueryBo, String> {
    NotifyConfig findByIdentifier(NotifyIdentifierEnum identifierEnum);


    List<String> findWechatTemplateIds();
}
