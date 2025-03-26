package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IOrdersData;
import com.obast.charer.data.business.IOrdersSettleData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.OrdersRepository;
import com.obast.charer.data.model.TbOrders;
import com.obast.charer.enums.ChargeStartTypeEnum;
import com.obast.charer.enums.ChargeStopTypeEnum;
import com.obast.charer.enums.OrderSettleTypeEnum;
import com.obast.charer.enums.OrderStateEnum;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.order.OrdersSettle;
import com.obast.charer.qo.OrdersQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.*;

@Primary
@Service
@RequiredArgsConstructor
public class OrdersDataImpl extends AbstractCommonData<OrdersQueryBo>
        implements IOrdersData, IJPACommData<Orders, String>, IJPACommonData<Orders, OrdersQueryBo, String> {

    private final OrdersRepository baseRepository;

    @Autowired
    private IOrdersSettleData ordersSettleData;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbOrders.class;
    }

    @Override
    public Class<?> getTClass() {
        return Orders.class;
    }

    @Override
    public Paging<Orders> findPage(PageRequest<OrdersQueryBo> request) {
        Specification<TbOrders> specification = buildSpecification(request.getData());
        Page<TbOrders> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbOrders> list = page.getContent();
        List<Orders> newList = new ArrayList<>();
        for(TbOrders tbOrders: list) {
            newList.add(fillData(tbOrders));
        }
        long total = page.getTotalElements();
        return new Paging<>(total, newList);
    }

    @Override
    public List<Orders> findList(OrdersQueryBo queryBo) {
        Specification<TbOrders> specification = buildSpecification(queryBo);
        List<TbOrders> list = baseRepository.findAll(specification);
        List<Orders> newList = new ArrayList<>();
        for(TbOrders tbOrders: list) {
            newList.add(fillData(tbOrders));
        }
        return newList;
    }

    @Override
    public Map<String, Object> findSummary() {
        return baseRepository.findSummary();
    }

    @Override
    public Map<String, Object> findSummaryByCreateTime(String startTime, String endTime) {
        return baseRepository.findSummaryByCreateTime(startTime, endTime);
    }


    @Override
    public Orders findById(String id) {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("id"), id);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbOrders entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Override
    public Orders findByTranId(String tranId) {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("tranId"), tranId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbOrders entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    @Override
    public Orders findByParkId(String parkId) {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("parkId"), parkId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbOrders entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }

    //根据占位记录查询订单
    @Override
    public Orders findByPark(String chargerId, String gunNo, Date inTime, Date outTime) {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("chargerId"), chargerId));
            predicates.add(cb.equal(root.get("gunNo"), gunNo));
            predicates.add(cb.equal(root.get("state"), OrderStateEnum.Settled));
            predicates.add(cb.greaterThanOrEqualTo(root.get("startTime"), inTime));
            predicates.add(cb.lessThanOrEqualTo(root.get("endTime"), outTime));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbOrders entity = baseRepository.findOne(specification).orElse(null);
        return fillData(entity);
    }


    @Override
    public List<Orders> findByCustomerIdAndState(String customerId, OrderStateEnum state) {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("customerId"), customerId);
            predicates.add(predicate1);

            Predicate predicate2 = cb.equal(root.get("state"), state.getCode());
            predicates.add(predicate2);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbOrders> list = baseRepository.findAll(specification);

        List<Orders> newList = new ArrayList<>();
        for(TbOrders tbOrders: list) {
            newList.add(fillData(tbOrders));
        }
        return newList;
    }

    @Override
    public List<Orders> findByUserName(String userName) {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate1 = cb.equal(root.get("userName"), userName);
            predicates.add(predicate1);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbOrders> list = baseRepository.findAll(specification);

        List<Orders> newList = new ArrayList<>();
        for(TbOrders tbOrders: list) {
            newList.add(fillData(tbOrders));
        }
        return newList;
    }

    @Override
    public List<Orders> findWaitStartOrders() {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("chargeStartType"), ChargeStartTypeEnum.Timer));
            predicates.add(cb.equal(root.get("state"), OrderStateEnum.Pending));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbOrders> list = baseRepository.findAll(specification);

        List<Orders> newList = new ArrayList<>();
        for(TbOrders tbOrders: list) {
            newList.add(fillData(tbOrders));
        }
        return newList;
    }

    @Override
    public List<Orders> findWaitStopOrders() {
        Specification<TbOrders> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("chargeStopType"), ChargeStopTypeEnum.TimedStop));
            predicates.add(cb.equal(root.get("state"), OrderStateEnum.Processing));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbOrders> list = baseRepository.findAll(specification);

        List<Orders> newList = new ArrayList<>();
        for(TbOrders tbOrders: list) {
            newList.add(fillData(tbOrders));
        }
        return newList;
    }

    /*
     * 生成订单
     */
    @Override
    public Orders add(Orders orders) {
        orders.setTranId(this.generateTranId());
        TbOrders tbOrders = MapstructUtils.convert(orders, TbOrders.class);
        if(tbOrders == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.save(tbOrders), Orders.class);
    }

    /*
     * 订单修改
     */
    @Override
    public Orders update(Orders orders) {
        TbOrders tbOrders = MapstructUtils.convert(orders, TbOrders.class);
        if(tbOrders == null) {
            return null;
        }
        return MapstructUtils.convert(baseRepository.save(tbOrders), Orders.class);
    }

    private Orders fillData(TbOrders tbOrders) {
        Orders orders = MapstructUtils.convert(tbOrders, Orders.class);
        assert orders != null;



        if(orders.getState().equals(OrderStateEnum.Settled)) {

            List<OrdersSettle> ordersSettles = ordersSettleData.findListByOrderId(tbOrders.getId());

            BigDecimal orderAmount = new BigDecimal(0);
            BigDecimal debitAmount = new BigDecimal(0);
            BigDecimal parkAmount = new BigDecimal(0);

            BigDecimal settledAmount = new BigDecimal(0);
            BigDecimal settledElecAmount = new BigDecimal(0);
            BigDecimal settledServiceAmount = new BigDecimal(0);
            BigDecimal settledParkAmount = new BigDecimal(0);

            BigDecimal discountAmount = new BigDecimal(0);
            BigDecimal discountElecAmount = new BigDecimal(0);
            BigDecimal discountServiceAmount = new BigDecimal(0);
            BigDecimal discountParkAmount = new BigDecimal(0);

            for(OrdersSettle ordersSettle: ordersSettles) {
                settledAmount = settledAmount.add(ordersSettle.getSettledAmount());
                discountAmount = discountAmount.add(ordersSettle.getDiscountAmount());
                if(ordersSettle.getType().equals(OrderSettleTypeEnum.Elec)) {
                    settledElecAmount = settledElecAmount.add(ordersSettle.getSettledAmount());
                    discountElecAmount = discountElecAmount.add(ordersSettle.getDiscountAmount());
                }
                if(ordersSettle.getType().equals(OrderSettleTypeEnum.Service)) {
                    settledServiceAmount = settledServiceAmount.add(ordersSettle.getSettledAmount());
                    discountServiceAmount = discountServiceAmount.add(ordersSettle.getDiscountAmount());
                }
                if(ordersSettle.getType().equals(OrderSettleTypeEnum.Park)) {
                    settledParkAmount = settledParkAmount.add(ordersSettle.getSettledAmount());
                    discountParkAmount = discountParkAmount.add(ordersSettle.getDiscountAmount());

                    parkAmount = parkAmount.add(ordersSettle.getAmount());
                }
            }

            orderAmount = orders.getTotalAmount().add(parkAmount);
            debitAmount = orderAmount.subtract(settledAmount).subtract(discountAmount);

            orders.setOrderAmount(orderAmount);
            orders.setParkAmount(parkAmount);
            orders.setSettledAmount(settledAmount);
            orders.setSettledElecAmount(settledElecAmount);
            orders.setSettledServiceAmount(settledServiceAmount);
            orders.setSettledParkAmount(settledParkAmount);
            orders.setDiscountAmount(discountAmount);
            orders.setDiscountElecAmount(discountElecAmount);
            orders.setDiscountServiceAmount(discountServiceAmount);
            orders.setDiscountParkAmount(discountParkAmount);


            orders.setDebitAmount(debitAmount.negate());
        }

        return orders;
    }

    public Specification<TbOrders> buildSpecification(OrdersQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getKeyword())) {
                String keyword = bo.getKeyword();
                Predicate predicateOr1 = cb.like(root.get("tranId").as(String.class), "%" + keyword + "%");
                Predicate predicateOr2 = cb.like(root.get("userName").as(String.class), "%" + keyword + "%");
                Predicate predicateOr3 = cb.like(root.get("chargerDn").as(String.class), "%" + keyword + "%");

                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate predicateOr4 = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("stationName"), cb.literal(String.format("$.%s", requestLang))), "%" + keyword + "%");

                Predicate predicateOr = cb.or(predicateOr1, predicateOr2, predicateOr3, predicateOr4);
                predicates.add(predicateOr);
            }

            if(StringUtils.isNoneBlank(bo.getUserName())) {
                Predicate predicate = cb.equal(root.get("userName"), bo.getUserName());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getCustomerId())) {
                Predicate predicate = cb.equal(root.get("customerId"), bo.getCustomerId());
                predicates.add(predicate);
            }

            if(bo.getState() != null) {
                Predicate predicate = cb.equal(root.get("state"), bo.getState().getCode());
                predicates.add(predicate);
            }

            if(bo.getCreateTime() != null && bo.getCreateTime().length > 0) {
                Date startTime = null;
                Date endTime  = null;
                if(bo.getCreateTime().length == 1) {
                     startTime = bo.getCreateTime()[0];
                } else if(bo.getCreateTime().length == 2) {
                    startTime = bo.getCreateTime()[0];
                    endTime  = bo.getCreateTime()[1];
                }

                if(startTime != null) {
                    Predicate predicate = cb.greaterThanOrEqualTo(root.get("createTime"), startTime);
                    predicates.add(predicate);
                }

                if(endTime != null) {
                    Predicate predicate = cb.lessThanOrEqualTo(root.get("createTime"), endTime);
                    predicates.add(predicate);
                }

            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    private String generateTranId() {
        return IdGenerator.generateOrderTranId();
    }

}
