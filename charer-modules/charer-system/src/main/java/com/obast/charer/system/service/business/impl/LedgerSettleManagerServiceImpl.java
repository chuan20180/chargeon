package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ILedgerData;
import com.obast.charer.data.business.ILedgerSettleData;
import com.obast.charer.data.business.ILedgerSettleDealerData;
import com.obast.charer.data.system.ISysAgentData;
import com.obast.charer.data.system.ISysDealerData;
import com.obast.charer.data.system.ISysTenantData;
import com.obast.charer.system.dto.bo.LedgerSettleBo;
import com.obast.charer.system.dto.vo.ledger.LedgerSettleVo;
import com.obast.charer.enums.LedgerSettleStateEnum;
import com.obast.charer.enums.LedgerStateEnum;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.system.service.business.ILedgerSettleManagerService;
import com.obast.charer.model.ledger.Ledger;
import com.obast.charer.model.ledger.LedgerSettle;
import com.obast.charer.model.ledger.LedgerSettleDealer;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.model.system.SysDealer;
import com.obast.charer.model.system.SysTenant;
import com.obast.charer.qo.LedgerSettleQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：分成结算管理服务实现
 */
@Service
public class LedgerSettleManagerServiceImpl implements ILedgerSettleManagerService {

    @Autowired
    private ILedgerData ledgerData;

    @Autowired
    private ISysDealerData sysDealerData;

    @Autowired
    private ISysTenantData sysTenantData;

    @Autowired
    private ISysAgentData sysAgentData;

    @Autowired
    private ILedgerSettleData ledgerSettleData;

    @Autowired
    private ILedgerSettleDealerData ledgerSettleDealerData;


    @Override
    public Paging<LedgerSettleVo> queryPageList(PageRequest<LedgerSettleQueryBo> pageRequest) {
        Paging<LedgerSettle> pageList = ledgerSettleData.findPage(pageRequest);
        Paging<LedgerSettleVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(LedgerSettle ledgerSettle: pageList.getRows()) {
            newPageList.getRows().add(fillData(ledgerSettle));
        }
        return newPageList;
    }

    @Override
    public List<LedgerSettleVo> queryList(PageRequest<LedgerSettleQueryBo> pageRequest) {
        List<LedgerSettle> list = ledgerSettleData.findList(pageRequest.getData());
        List<LedgerSettleVo> newList = new ArrayList<>();
        for(LedgerSettle ledgerSettle: list) {
            newList.add(fillData(ledgerSettle));
        }
        return newList;
    }


    @Override
    public LedgerSettleVo queryDetail(String id) {
        return MapstructUtils.convert(ledgerSettleData.findById(id), LedgerSettleVo.class );
    }

    @Override
    @Transactional
    public boolean addLedgerSettle(LedgerSettleBo bo) {
        List<String> ledgerIds = bo.getLedgerIds();
        if(ledgerIds == null || ledgerIds.isEmpty()) {
            throw new BizException(ErrCode.LEDGER_SELECTION_EMPTY);
        }

        Map<String, List<Ledger>> dealerMap = new HashMap<>();

        LedgerSettle ledgerSettle = new LedgerSettle();

        LedgerSettle savedLedgerSettle = ledgerSettleData.add(ledgerSettle);

        for(String ledgerId: ledgerIds) {
            Ledger ledger = ledgerData.findById(ledgerId);
            if(ledger == null) {
                throw new BizException(ErrCode.LEDGER_NOT_FOUND);
            }

            if(ledger.getType().equals(LedgerTypeEnum.Platform)) {
                String key = String.format("%s_%s", LedgerTypeEnum.Platform.name(), LedgerTypeEnum.Platform.name());
                dealerMap.computeIfAbsent(key, k -> new ArrayList<>());
                dealerMap.get(key).add(ledger);
            } else if(ledger.getType().equals(LedgerTypeEnum.Tenant)) {
                String key = String.format("%s_%s", LedgerTypeEnum.Tenant, ledger.getTenantId());
                dealerMap.computeIfAbsent(key, k -> new ArrayList<>());
                dealerMap.get(key).add(ledger);
            } else if(ledger.getType().equals(LedgerTypeEnum.Agent)) {
                String key = String.format("%s_%s", LedgerTypeEnum.Agent, ledger.getAgentId());
                dealerMap.computeIfAbsent(key, k -> new ArrayList<>());
                dealerMap.get(key).add(ledger);
            } else if(ledger.getType().equals(LedgerTypeEnum.Dealer)) {
                String key = String.format("%s_%s", LedgerTypeEnum.Dealer, ledger.getDealerId());
                dealerMap.computeIfAbsent(key, k -> new ArrayList<>());
                dealerMap.get(key).add(ledger);
            }
        }

        BigDecimal totalAmount = new BigDecimal(0);

        for (Map.Entry<String, List<Ledger>> dealer : dealerMap.entrySet()) {
            String[] keyIds = dealer.getKey().split("_");
            String type = keyIds[0];
            String id = keyIds[1];
            List<Ledger> ledgers = dealer.getValue();

            LedgerTypeEnum typeEnum = LedgerTypeEnum.valueOf(type);

            if(typeEnum.equals(LedgerTypeEnum.Platform)) {
                BigDecimal amount = new BigDecimal(0);
                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        amount = amount.add(ledger.getAmount());
                    }
                }
                totalAmount = totalAmount.add(amount);

                LedgerSettleDealer ledgerSettleDealer = new LedgerSettleDealer();
                ledgerSettleDealer.setLedgerSettleId(savedLedgerSettle.getId());
                ledgerSettleDealer.setType(typeEnum);

                ledgerSettleDealer.setDealerId(null);
                ledgerSettleDealer.setDealerName(null);
                ledgerSettleDealer.setAgentId(null);
                ledgerSettleDealer.setAgentName(null);
                ledgerSettleDealer.setTenantId(null);
                ledgerSettleDealer.setTenantName(null);

                ledgerSettleDealer.setAmount(amount);
                ledgerSettleDealer.setState(LedgerSettleStateEnum.Settled);
                ledgerSettleDealer.setSettleTime(new Date());

                LedgerSettleDealer savedLedgerSettleDealer = ledgerSettleDealerData.add(ledgerSettleDealer);

                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        ledger.setLedgerSettleId(savedLedgerSettle.getId());
                        ledger.setLedgerSettleDealerId(savedLedgerSettleDealer.getId());
                        ledger.setState(LedgerStateEnum.Settled);
                        ledgerData.save(ledger);
                    }
                }
            } else if(typeEnum.equals(LedgerTypeEnum.Tenant)) {

                SysTenant sysTenant = sysTenantData.findById(id);
                if(sysTenant == null) {
                    throw new BizException(ErrCode.TENANT_NOT_FOUND);
                }

                BigDecimal amount = new BigDecimal(0);
                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        amount = amount.add(ledger.getAmount());
                    }
                }
                totalAmount = totalAmount.add(amount);

                LedgerSettleDealer ledgerSettleDealer = new LedgerSettleDealer();
                ledgerSettleDealer.setLedgerSettleId(savedLedgerSettle.getId());
                ledgerSettleDealer.setType(typeEnum);

                ledgerSettleDealer.setDealerId(null);
                ledgerSettleDealer.setDealerName(null);
                ledgerSettleDealer.setAgentId(null);
                ledgerSettleDealer.setAgentName(null);
                ledgerSettleDealer.setTenantId(sysTenant.getId());
                ledgerSettleDealer.setTenantName(sysTenant.getCompanyName());

                ledgerSettleDealer.setAmount(amount);
                ledgerSettleDealer.setState(LedgerSettleStateEnum.Settled);
                ledgerSettleDealer.setSettleTime(new Date());

                LedgerSettleDealer savedLedgerSettleDealer = ledgerSettleDealerData.add(ledgerSettleDealer);

                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        ledger.setLedgerSettleId(savedLedgerSettle.getId());
                        ledger.setLedgerSettleDealerId(savedLedgerSettleDealer.getId());
                        ledger.setState(LedgerStateEnum.Settled);
                        ledgerData.save(ledger);
                    }
                }
            } else if(typeEnum.equals(LedgerTypeEnum.Agent)) {

                SysAgent sysAgent = sysAgentData.findById(id);
                if(sysAgent == null) {
                    throw new BizException(ErrCode.SYS_AGENT_NOT_FOUND);
                }

                SysTenant sysTenant = sysTenantData.findById(sysAgent.getTenantId());
                if(sysTenant == null) {
                    throw new BizException(ErrCode.TENANT_NOT_FOUND);
                }

                BigDecimal amount = new BigDecimal(0);
                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        amount = amount.add(ledger.getAmount());
                    }
                }
                totalAmount = totalAmount.add(amount);

                LedgerSettleDealer ledgerSettleDealer = new LedgerSettleDealer();
                ledgerSettleDealer.setLedgerSettleId(savedLedgerSettle.getId());
                ledgerSettleDealer.setType(typeEnum);

                ledgerSettleDealer.setDealerId(null);
                ledgerSettleDealer.setDealerName(null);
                ledgerSettleDealer.setAgentId(sysAgent.getId());
                ledgerSettleDealer.setAgentName(sysAgent.getName());
                ledgerSettleDealer.setTenantId(sysTenant.getId());
                ledgerSettleDealer.setTenantName(sysTenant.getCompanyName());

                ledgerSettleDealer.setAmount(amount);
                ledgerSettleDealer.setState(LedgerSettleStateEnum.Settled);
                ledgerSettleDealer.setSettleTime(new Date());

                LedgerSettleDealer savedLedgerSettleDealer = ledgerSettleDealerData.add(ledgerSettleDealer);

                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        ledger.setLedgerSettleId(savedLedgerSettle.getId());
                        ledger.setLedgerSettleDealerId(savedLedgerSettleDealer.getId());
                        ledger.setState(LedgerStateEnum.Settled);
                        ledgerData.save(ledger);
                    }
                }
            } else if(typeEnum.equals(LedgerTypeEnum.Dealer)) {

                SysDealer sysDealer = sysDealerData.findById(id);
                if(sysDealer == null) {
                    throw new BizException(ErrCode.SYS_DEALER_NOT_FOUND);
                }

                SysAgent sysAgent = sysAgentData.findById(sysDealer.getAgentId());
                if(sysAgent == null) {
                    throw new BizException(ErrCode.SYS_AGENT_NOT_FOUND);
                }

                SysTenant sysTenant = sysTenantData.findById(sysDealer.getTenantId());
                if(sysTenant == null) {
                    throw new BizException(ErrCode.TENANT_NOT_FOUND);
                }

                BigDecimal amount = new BigDecimal(0);
                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        amount = amount.add(ledger.getAmount());
                    }
                }
                totalAmount = totalAmount.add(amount);

                LedgerSettleDealer ledgerSettleDealer = new LedgerSettleDealer();
                ledgerSettleDealer.setLedgerSettleId(savedLedgerSettle.getId());
                ledgerSettleDealer.setType(typeEnum);

                ledgerSettleDealer.setDealerId(sysDealer.getId());
                ledgerSettleDealer.setDealerName(sysDealer.getName());
                ledgerSettleDealer.setAgentId(sysAgent.getId());
                ledgerSettleDealer.setAgentName(sysAgent.getName());
                ledgerSettleDealer.setTenantId(sysTenant.getId());
                ledgerSettleDealer.setTenantName(sysTenant.getCompanyName());

                ledgerSettleDealer.setAmount(amount);
                ledgerSettleDealer.setState(LedgerSettleStateEnum.Settled);
                ledgerSettleDealer.setSettleTime(new Date());

                LedgerSettleDealer savedLedgerSettleDealer = ledgerSettleDealerData.add(ledgerSettleDealer);

                if(!ledgers.isEmpty()) {
                    for(Ledger ledger: ledgers) {
                        ledger.setLedgerSettleId(savedLedgerSettle.getId());
                        ledger.setLedgerSettleDealerId(savedLedgerSettleDealer.getId());
                        ledger.setState(LedgerStateEnum.Settled);
                        ledgerData.save(ledger);
                    }
                }
            }
        }

        savedLedgerSettle.setAmount(totalAmount);
        ledgerSettleData.save(savedLedgerSettle);
        return true;
    }

    private LedgerSettleVo fillData(LedgerSettle ledgerSettle) {
        LedgerSettleVo vo = MapstructUtils.convert(ledgerSettle, LedgerSettleVo.class);
        if(vo == null) {
            return null;
        }

        vo.setDealers(ledgerSettleDealerData.findByLedgerSettleId(vo.getId()));

        return vo;
    }
}
