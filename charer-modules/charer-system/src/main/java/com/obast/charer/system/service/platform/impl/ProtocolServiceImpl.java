package com.obast.charer.system.service.platform.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.platform.IProtocolData;
import com.obast.charer.model.protocol.Protocol;
import com.obast.charer.qo.ProtocolQueryBo;
import com.obast.charer.system.dto.bo.ProtocolBo;
import com.obast.charer.system.dto.vo.ProtocolVo;
import com.obast.charer.system.service.platform.IProtocolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProtocolServiceImpl implements IProtocolService {

    @Autowired
    private IProtocolData protocolData;

    @Override
    public Paging<ProtocolVo> queryPageList(PageRequest<ProtocolQueryBo> pageRequest) {
        return protocolData.findPage(pageRequest).to(ProtocolVo.class);
    }

    @Override
    public List<ProtocolVo> queryList(PageRequest<ProtocolQueryBo> pageRequest) {
        return MapstructUtils.convert(protocolData.findList(pageRequest.getData()), ProtocolVo.class);
    }

    @Override
    public ProtocolVo queryDetail(String id) {
        return protocolData.findById(id).to(ProtocolVo.class);
    }

    @Override
    public ProtocolVo add(ProtocolBo data) {
        Protocol protocol = data.to(Protocol.class);
        String protocolKey = data.getProtocolKey();
        Protocol oldProtocol = protocolData.findByProtocolKey(protocolKey);
        if (oldProtocol != null) {
            throw new BizException(ErrCode.PRODUCT_KEY_EXIST);
        }
        protocolData.save(protocol);
        return MapstructUtils.convert(protocol, ProtocolVo.class);
    }

    @Override
    public boolean update(ProtocolBo protocolBo) {
        Protocol protocol = protocolBo.to(Protocol.class);
        protocolData.save(protocol);
        return true;
    }

    @Override
    public void updateStatus(ProtocolBo bo) {
        Protocol protocol = protocolData.findById(bo.getId());
        protocol.setStatus(bo.getStatus());
        protocolData.save(protocol);
    }


    @Override
    public boolean delete(String id) {
        Protocol protocol = protocolData.findById(id);
        if (Objects.isNull(protocol)) {
            throw new BizException(ErrCode.PRODUCT_NOT_FOUND);
        }
        protocolData.deleteById(protocol.getId());
        return true;
    }
}
