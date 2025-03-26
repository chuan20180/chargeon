package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.LedgerSettleDealerRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ILedgerSettleDealerData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.model.TbLedgerSettleDealer;
import com.obast.charer.enums.LedgerTypeEnum;
import com.obast.charer.model.ledger.LedgerSettleDealer;
import com.obast.charer.qo.LedgerSettleDealerQueryBo;
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
public class LedgerSettleDealerDataImpl extends AbstractCommonData<LedgerSettleDealerQueryBo>
        implements ILedgerSettleDealerData, IJPACommData<LedgerSettleDealer, String>, IJPACommonData<LedgerSettleDealer, LedgerSettleDealerQueryBo, String> {

    private final LedgerSettleDealerRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbLedgerSettleDealer.class; }

    @Override
    public Class<?> getTClass() {
        return LedgerSettleDealer.class;
    }

    @Override
    public Paging<LedgerSettleDealer> findPage(PageRequest<LedgerSettleDealerQueryBo> request) {
        Specification<TbLedgerSettleDealer> specification = buildSpecification(request.getData());
        Page<TbLedgerSettleDealer> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbLedgerSettleDealer> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, LedgerSettleDealer.class));
    }

    @Override
    public List<LedgerSettleDealer> findList(LedgerSettleDealerQueryBo queryBo) {
        Specification<TbLedgerSettleDealer> specification = buildSpecification(queryBo);
        List<TbLedgerSettleDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, LedgerSettleDealer.class);
    }

    @Override
    public List<LedgerSettleDealer> findByLedgerSettleId(String ledgerSettleId) {
        Specification<TbLedgerSettleDealer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("ledgerSettleId"), ledgerSettleId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbLedgerSettleDealer> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, LedgerSettleDealer.class);
    }

    @Override
    public LedgerSettleDealer add(LedgerSettleDealer ledgerSettleDealer) {
        ledgerSettleDealer.setTranId(generateTranId());
        TbLedgerSettleDealer tbLedgerSettleDealer = baseRepository.saveAndFlush(MapstructUtils.convert(ledgerSettleDealer, TbLedgerSettleDealer.class));
        return MapstructUtils.convert(tbLedgerSettleDealer, LedgerSettleDealer.class);
    }

    public Specification<TbLedgerSettleDealer> buildSpecification(LedgerSettleDealerQueryBo bo) {
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

            if(bo.getState() != null) {
                Predicate predicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateLedgerSettleDealerTranId();
    }

}
