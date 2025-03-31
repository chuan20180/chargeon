package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysLoginInfo;
import com.obast.charer.qo.SysLoginInfoQueryBo;

import java.util.List;

/**
 * 登录记录数据接口
 *
 * @author sjg
 */
public interface ISysLoginInfoData  extends ICommonData<SysLoginInfo, String>, IJPACommonData<SysLoginInfo, SysLoginInfoQueryBo, String> {
}
