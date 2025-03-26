package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IOrdersSettleData;
import com.obast.charer.data.dao.OrdersSettleRepository;
import com.obast.charer.data.model.TbOrdersSettle;
import com.obast.charer.model.order.OrdersSettle;
import com.obast.charer.qo.OrdersSettleQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class OrdersSettleDataImpl extends AbstractCommonData<OrdersSettleQueryBo>
        implements IOrdersSettleData, IJPACommData<OrdersSettle, String>, IJPACommonData<OrdersSettle, OrdersSettleQueryBo, String> {

    private final OrdersSettleRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbOrdersSettle.class;
    }

    @Override
    public Class<?> getTClass() {
        return OrdersSettle.class;
    }

    @Override
    public Paging<OrdersSettle> findPage(PageRequest<OrdersSettleQueryBo> request) {
        Specification<TbOrdersSettle> specification = buildSpecification(request.getData());
        Page<TbOrdersSettle> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbOrdersSettle> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, OrdersSettle.class));
    }

    @Override
    public List<OrdersSettle> findList(OrdersSettleQueryBo queryBo) {
        Specification<TbOrdersSettle> specification = buildSpecification(queryBo);
        List<TbOrdersSettle> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, OrdersSettle.class);
    }


    @Override
    public List<OrdersSettle> findListByOrderId(String orderId) {
        Specification<TbOrdersSettle> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("orderId"), orderId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbOrdersSettle> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, OrdersSettle.class);
    }

    @Override
    public BigDecimal findAmountByCustomerId(String customerId) {
        return baseRepository.findAmountByCustomerId(customerId);
    }

    /*
     * 添加
     */
    @Override
    public OrdersSettle add(OrdersSettle ordersSettle) {
        TbOrdersSettle tbOrdersSettle = MapstructUtils.convert(ordersSettle, TbOrdersSettle.class);
        if(tbOrdersSettle == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.saveAndFlush(tbOrdersSettle), OrdersSettle.class);
    }

    @Override
    public OrdersSettle update(OrdersSettle ordersSettle) {
        if(StringUtils.isBlank(ordersSettle.getId())) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }

        TbOrdersSettle tbOrdersSettle = MapstructUtils.convert(ordersSettle, TbOrdersSettle.class);
        if(tbOrdersSettle == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.saveAndFlush(tbOrdersSettle), OrdersSettle.class);
    }

    public Specification<TbOrdersSettle> buildSpecification(OrdersSettleQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getOrderId())) {
                Predicate predicate = cb.equal(root.get("orderId"), bo.getOrderId());
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
