package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.LedgerSettleRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.business.ILedgerSettleData;
import com.obast.charer.data.model.TbLedgerSettle;
import com.obast.charer.model.ledger.LedgerSettle;
import com.obast.charer.qo.LedgerSettleQueryBo;
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
public class LedgerSettleDataImpl extends AbstractCommonData<LedgerSettleQueryBo>
        implements ILedgerSettleData, IJPACommData<LedgerSettle, String>, IJPACommonData<LedgerSettle, LedgerSettleQueryBo, String> {

    private final LedgerSettleRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() { return baseRepository; }

    @Override
    public Class<?> getJpaRepositoryClass() { return TbLedgerSettle.class;  }

    @Override
    public Class<?> getTClass() {
        return LedgerSettle.class;
    }


    @Override
    public Paging<LedgerSettle> findPage(PageRequest<LedgerSettleQueryBo> request) {
        Specification<TbLedgerSettle> specification = buildSpecification(request.getData());
        Page<TbLedgerSettle> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbLedgerSettle> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, LedgerSettle.class));
    }

    @Override
    public List<LedgerSettle> findList(LedgerSettleQueryBo queryBo) {
        Specification<TbLedgerSettle> specification = buildSpecification(queryBo);
        List<TbLedgerSettle> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, LedgerSettle.class);
    }

    @Override
    public LedgerSettle add(LedgerSettle ledgerSettle) {
        ledgerSettle.setTranId(generateTranId());
        TbLedgerSettle tbLedgerSettle = baseRepository.saveAndFlush(MapstructUtils.convert(ledgerSettle, TbLedgerSettle.class));
        return MapstructUtils.convert(tbLedgerSettle, LedgerSettle.class);
    }

    public Specification<TbLedgerSettle> buildSpecification(LedgerSettleQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateLedgerSettleTranId();
    }

}
