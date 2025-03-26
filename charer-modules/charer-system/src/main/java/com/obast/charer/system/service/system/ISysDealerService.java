package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysDealerBo;
import com.obast.charer.system.dto.vo.SysDealerVo;
import com.obast.charer.qo.SysDealerQueryBo;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：租户分帐服务接口
 */
public interface ISysDealerService {
    Paging<SysDealerVo> queryPageList(PageRequest<SysDealerQueryBo> pageRequest);

    List<SysDealerVo> queryList(PageRequest<SysDealerQueryBo> pageRequest);

    SysDealerVo queryDetail(String stationId);

    boolean addDealer(SysDealerBo data);

    boolean updateDealer(SysDealerBo data);

    void updateStatus(SysDealerBo bo);

    boolean deleteDealer(String id);

    boolean batchDeleteDealer(List<String> ids);


}
