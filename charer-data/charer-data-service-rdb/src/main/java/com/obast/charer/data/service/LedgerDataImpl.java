package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.LedgerRepository;
import com.obast.charer.data.business.ILedgerData;
import com.obast.charer.data.model.TbLedger;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.model.ledger.Ledger;
import com.obast.charer.qo.LedgerQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class LedgerDataImpl extends AbstractCommonData<LedgerQueryBo>
        implements ILedgerData, IJPACommData<Ledger, String>, IJPACommonData<Ledger, LedgerQueryBo, String> {


    private final LedgerRepository baseRepository;


    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbLedger.class;
    }

    @Override
    public Class<?> getTClass() {
        return Ledger.class;
    }

    @Override
    public Paging<Ledger> findPage(PageRequest<LedgerQueryBo> request) {
        Specification<TbLedger> specification = buildSpecification(request.getData());
        Page<TbLedger> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbLedger> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Ledger.class));
    }

    @Override
    public List<Ledger> findList(LedgerQueryBo queryBo) {
        Specification<TbLedger> specification = buildSpecification(queryBo);
        List<TbLedger> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Ledger.class);
    }

    /*
     * 生成订单
     */
    @Override
    public Ledger add(Ledger ledger) {
        ledger.setTranId(this.generateTranId());

        TbLedger tbLedger = MapstructUtils.convert(ledger, TbLedger.class);
        if(tbLedger == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.save(tbLedger), Ledger.class);
    }

    public Specification<TbLedger> buildSpecification(LedgerQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getTranId())) {
                Predicate predicate = cb.equal(root.get("tranId"), bo.getTranId());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getName())) {
                String keyword = bo.getName();
                if(keyword.equals(LedgerTypeEnum.Platform.getMsg())) {
                    Predicate predicate = cb.equal(root.get("type"), bo.getType());
                    predicates.add(predicate);
                } else {
                    Predicate predicateOr1 = cb.like(root.get("agentName").as(String.class), keyword);
                    Predicate predicateOr2 = cb.like(root.get("dealerName").as(String.class), keyword);
                    Predicate predicateOr3 = cb.like(root.get("tenantName").as(String.class), keyword);

                    Predicate predicateOr = cb.or(predicateOr1, predicateOr2, predicateOr3);
                    predicates.add(predicateOr);
                }
            }

            if(StringUtils.isNoneBlank(bo.getLedgerSettleId())) {
                Predicate predicate = cb.equal(root.get("ledgerSettleId"), bo.getLedgerSettleId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getLedgerSettleDealerId())) {
                Predicate predicate = cb.equal(root.get("ledgerSettleDealerId"), bo.getLedgerSettleDealerId());
                predicates.add(predicate);
            }

            if(bo.getState() != null) {
                Predicate predicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateLedgerTranId();
    }

}
