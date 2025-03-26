package com.obast.charer.data.system;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.Storage;
import com.obast.charer.qo.StorageQueryBo;

public interface IStorageData extends ICommonData<Storage, String>, IJPACommonData<Storage, StorageQueryBo, String> {

    boolean checkDnUnique(Storage ChargerStorage);

    Storage add(Storage ChargerStorage);

    Storage update(Storage ChargerStorage);


}
