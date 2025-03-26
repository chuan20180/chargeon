package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysCountry;
import com.obast.charer.qo.SysCountryQueryBo;

public interface ISysCountryData extends ICommonData<SysCountry, String>, IJPACommonData<SysCountry, SysCountryQueryBo, String> {

    SysCountry add(SysCountry sysCountry);

    SysCountry update(SysCountry sysCountry);
}
