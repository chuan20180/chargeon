package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.system.ISysAgentStationData;
import com.obast.charer.data.system.ISysAgentStationDealerData;
import com.obast.charer.system.dto.bo.SysAgentStationDealerBo;
import com.obast.charer.system.dto.bo.SysAgentStationDealerSaveBo;
import com.obast.charer.system.dto.vo.SysAgentStationDealerVo;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.system.service.system.ISysAgentStationDealerService;
import com.obast.charer.model.system.SysAgentStation;
import com.obast.charer.model.system.SysAgentStationDealer;
import com.obast.charer.qo.SysAgentStationDealerQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：代理商和合作商绑定管理服务实现
 */
@Service
public class SysAgentStationDealerServiceImpl implements ISysAgentStationDealerService {

    @Autowired
    private ISysAgentStationDealerData sysAgentStationDealerData;

    @Autowired
    private ISysAgentStationData sysAgentStationData;

    @Override
    public Paging<SysAgentStationDealerVo> queryPageList(PageRequest<SysAgentStationDealerQueryBo> pageRequest) {
        Paging<SysAgentStationDealer> pageList = sysAgentStationDealerData.findPage(pageRequest);
        Paging<SysAgentStationDealerVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysAgentStationDealer sysAgentStationDealer : pageList.getRows()) {
            newPageList.getRows().add(fillData(sysAgentStationDealer));
        }
        return newPageList;
    }

    @Override
    public List<SysAgentStationDealerVo> queryList(PageRequest<SysAgentStationDealerQueryBo> pageRequest) {
        List<SysAgentStationDealer> list = sysAgentStationDealerData.findList(pageRequest.getData());
        List<SysAgentStationDealerVo> newList = new ArrayList<>();
        for(SysAgentStationDealer sysAgent: list) {
            newList.add(fillData(sysAgent));
        }
        return newList;
    }

    @Override
    public SysAgentStationDealerVo queryDetail(String id) {
        return fillData(sysAgentStationDealerData.findById(id));
    }


    private SysAgentStationDealerVo fillData(SysAgentStationDealer sysAgentStationDealer) {
        return MapstructUtils.convert(sysAgentStationDealer, SysAgentStationDealerVo.class);
    }

    @Override
    @Transactional
    public boolean save(SysAgentStationDealerSaveBo bo) {
        String agentStationId = bo.getAgentStationId();
        if(StringUtils.isBlank(agentStationId)) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }

        SysAgentStation sysAgentStation = sysAgentStationData.findById(bo.getAgentStationId());
        if(sysAgentStation == null) {
            throw new BizException(ErrCode.SYS_AGENT_STATION_NOT_FOUND);
        }

        List<SysAgentStationDealer> agentStationDealers = sysAgentStationDealerData.findByAgentStationId(sysAgentStation.getId());
        if(agentStationDealers != null) {
            List<String> agentStationDealerId = agentStationDealers.stream().map(SysAgentStationDealer::getId).collect(Collectors.toList());
            sysAgentStationDealerData.deleteByIds(agentStationDealerId);
        }

        List<SysAgentStationDealerBo> dealers = bo.getDealers();
        if(dealers == null || dealers.isEmpty()) {
            throw new BizException(ErrCode.SYS_AGENT_DEALERS_EMPTY);
        }

        Map<String, String> dealerMap = new HashMap<>();
        BigDecimal percents = new BigDecimal(0);
        for(SysAgentStationDealerBo sysAgentStationDealerBo : dealers) {
            String dealerId = sysAgentStationDealerBo.getDealerId();
            if(StringUtils.isBlank(dealerId)) {
                throw new BizException(ErrCode.SYS_AGENT_DEALER_DEALER_ID_EMPTY);
            }

            if(sysAgentStationDealerBo.getPercent() == null) {
                throw new BizException(ErrCode.SYS_AGENT_DEALER_PERCENT_EMPTY);
            }

            if(dealerMap.containsKey(dealerId)) {
                throw new BizException(ErrCode.SYS_AGENT_DEALER_DEALER_ID_NOT_UNIQUE);
            } else {
                dealerMap.put(dealerId, dealerId);
            }

            percents = percents.add(sysAgentStationDealerBo.getPercent());
        }

        if(percents.compareTo(new BigDecimal(1)) > 0 || percents.compareTo(new BigDecimal(0)) < 0) {
            throw new BizException(ErrCode.SYS_AGENT_DEALER_PERCENT_INVALID);
        }

        for(SysAgentStationDealerBo sysAgentStationDealerBo : dealers) {
            SysAgentStationDealer sysAgentStationDealer = new SysAgentStationDealer();
            sysAgentStationDealer.setAgentId(sysAgentStation.getAgentId());
            sysAgentStationDealer.setAgentStationId(sysAgentStation.getId());
            sysAgentStationDealer.setDealerId(sysAgentStationDealerBo.getDealerId());
            sysAgentStationDealer.setPercent(sysAgentStationDealerBo.getPercent());
            sysAgentStationDealer.setStatus(EnableStatusEnum.Enabled);
            sysAgentStationDealerData.add(sysAgentStationDealer);
        }

        return true;
    }

    @Override
    public void updateStatus(SysAgentStationDealerBo bo) {
        SysAgentStationDealer sysAgentStationDealer = sysAgentStationDealerData.findById(bo.getId());
        sysAgentStationDealer.setStatus(bo.getStatus());
        sysAgentStationDealerData.save(sysAgentStationDealer);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        SysAgentStationDealer sysAgentStationDealer = sysAgentStationDealerData.findById(id);
        if(sysAgentStationDealer == null) {
            throw new BizException(ErrCode.SYS_AGENT_NOT_FOUND);
        }
        sysAgentStationDealerData.deleteById(sysAgentStationDealer.getId());
        return true;
    }

    @Override
    @Transactional
    public boolean batchDelete(List<String> ids) {
        for(String agentId: ids) {
            SysAgentStationDealer sysAgentStationDealer = sysAgentStationDealerData.findById(agentId);
            if (sysAgentStationDealer == null) {
                throw new BizException(ErrCode.SYS_AGENT_NOT_FOUND);
            }
            sysAgentStationDealerData.deleteById(sysAgentStationDealer.getId());
        }
        return true;
    }
}