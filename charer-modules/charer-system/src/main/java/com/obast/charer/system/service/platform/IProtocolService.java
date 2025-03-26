package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.ProtocolQueryBo;
import com.obast.charer.system.dto.bo.ProtocolBo;
import com.obast.charer.system.dto.vo.ProtocolVo;

import java.util.List;

/**
 * 协议服务接口
 */
public interface IProtocolService {

    Paging<ProtocolVo> queryPageList(PageRequest<ProtocolQueryBo> pageRequest);

    List<ProtocolVo> queryList(PageRequest<ProtocolQueryBo> pageRequest);

    ProtocolVo queryDetail(String productId);

    ProtocolVo add(ProtocolBo data);

    boolean update(ProtocolBo productBo);

    void updateStatus(ProtocolBo bo);

    boolean delete(String id);
}
