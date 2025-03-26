package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.ads.Ads;
import com.obast.charer.model.system.SysOss;
import com.obast.charer.qo.AdsQueryBo;
import com.obast.charer.qo.SysOssQueryBo;

/**
 * 文件数据接口
 *
 * @author sjg
 */
public interface ISysOssData extends ICommonData<SysOss, String>, IJPACommonData<SysOss, SysOssQueryBo, String> {
}
