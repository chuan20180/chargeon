package com.obast.charer.system.service.business;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.StorageQueryBo;
import com.obast.charer.system.dto.bo.StorageBatchBo;
import com.obast.charer.system.dto.bo.StorageBo;
import com.obast.charer.system.dto.bo.StorageStoreBo;
import com.obast.charer.system.dto.vo.StorageVo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩库管理服务接口
 */
public interface IStorageManagerService {
    Paging<StorageVo> queryPageList(PageRequest<StorageQueryBo> pageRequest);

    List<StorageVo> queryList(PageRequest<StorageQueryBo> pageRequest);

    StorageVo queryDetail(String id);

    void add(StorageBo data);

    void batchAdd(StorageBatchBo data);

    void update(StorageBo data);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);


    boolean batchStore(StorageStoreBo ids);
}
