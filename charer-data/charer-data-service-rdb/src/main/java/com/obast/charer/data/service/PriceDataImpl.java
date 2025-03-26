package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.model.dto.PriceInterval;
import com.obast.charer.common.model.dto.PricePeriod;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.data.dao.PriceRepository;
import com.obast.charer.data.model.price.TbPrice;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.price.Price;
import com.obast.charer.qo.PriceQueryBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class PriceDataImpl extends AbstractCommonData<PriceQueryBo>
        implements IPriceData, IJPACommData<Price, String>, IJPACommonData<Price, PriceQueryBo, String> {

    private final PriceRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbPrice.class;
    }

    @Override
    public Class<?> getTClass() {
        return Price.class;
    }

    @Override
    public Paging<Price> findPage(PageRequest<PriceQueryBo> request) {
        Specification<TbPrice> specification = buildSpecification(request.getData());
        Page<TbPrice> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPrice> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Price.class));
    }

    @Override
    public List<Price> findList(PriceQueryBo queryBo) {
        Specification<TbPrice> specification = buildSpecification(queryBo);
        List<TbPrice> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Price.class);
    }

    @Override
    public Price findById(String priceId) {
        TbPrice tbPrice = baseRepository.findById(priceId).orElse(null);
        return MapstructUtils.convert(tbPrice, Price.class);
    }

    @Override
    public Price findByName(String name) {
        PriceQueryBo bo = new PriceQueryBo();
        bo.setName(name);
        Specification<TbPrice> specification = buildSpecification(bo);
        TbPrice tbPrice = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(tbPrice, Price.class);
    }

    @Transactional
    @Override
    public Price add(Price price) {
        TbPrice bo = price.to(TbPrice.class);
        bo.setNo(this.generateNo());
        bo.setStatus(EnableStatusEnum.Enabled);
        TbPrice tbPrice = baseRepository.saveAndFlush(bo);
        return MapstructUtils.convert(tbPrice, Price.class);
    }

    @Transactional
    @Override
    public Price update(Price entity) {
        TbPrice bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Price.class);
    }

    public Specification<TbPrice> buildSpecification(PriceQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.like(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getType() != null) {
                Predicate predicate = cb.equal(root.get("type"), bo.getType().getCode());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public PriceFee calcCurrentPrice(Price price) {
        PriceProperties properties = price.getProperties();
        List<PriceFee> fees = properties.getFee();
        List<PricePeriod> periods = properties.getPeriod();
        PriceFee current = null;

        if(price.getType().equals(PriceTypeEnum.Standard)) {
            return fees.get(0);
        } else {
            for(PricePeriod period: periods) {
                String label = period.getLabel();
                String startHour = label.substring(0,2);
                String startMinute = label.substring(3,5);
                String endHour = label.substring(6,8);
                String endMinute = label.substring(9,11);

                SimpleDateFormat startSdf = new SimpleDateFormat(String.format("yyyy-MM-dd %s:%s:00", startHour, startMinute ));

                String startDateString = startSdf.format(new Date());

                SimpleDateFormat endSdf = new SimpleDateFormat(String.format("yyyy-MM-dd %s:%s:00", endHour, endMinute ));
                String endDateString = endSdf.format(new Date());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date now = new Date();
                    Date startDate = sdf.parse(startDateString);
                    Date endDate = sdf.parse(endDateString);

                    if(startDate.before(now) && endDate.after(now)) {
                        current = fees.get(period.getIndex());
                    }
                } catch (Exception e) {
                    log.error("获取计价规则当前价格异常 {}", e.getLocalizedMessage());
                }
            }
        }

        return current;
    }

    @Override
    public PriceFee calcMinPrice(Price price) {
        PriceProperties properties = price.getProperties();
        List<PriceFee> fees = properties.getFee();
        List<PricePeriod> periods = properties.getPeriod();
        PriceFee lowPrice = null;

        if(price.getType().equals(PriceTypeEnum.Standard)) {
            return fees.get(0);
        } else {
            List<PriceFee> usedFees = new ArrayList<>();
            for(PricePeriod period: periods) {
                if(!usedFees.contains(fees.get(period.getIndex()))) {
                    usedFees.add(fees.get(period.getIndex()));
                }
            }
            for(PriceFee fee: usedFees) {
                if(lowPrice == null) {
                    lowPrice = fee;
                    continue;
                }

                if(fee.getElecFee().add(fee.getServiceFee()).compareTo(lowPrice.getElecFee().add(lowPrice.getServiceFee())) < 0) {
                    lowPrice = fee;
                }
            }
        }
        return lowPrice;
    }

    @Override
    public PriceFee calcMaxPrice(Price price) {
        PriceProperties properties = price.getProperties();
        List<PriceFee> fees = properties.getFee();
        List<PricePeriod> periods = properties.getPeriod();
        PriceFee maxPrice = null;

        if(price.getType().equals(PriceTypeEnum.Standard)) {
            return fees.get(0);
        } else {
            List<PriceFee> usedFees = new ArrayList<>();
            for(PricePeriod period: periods) {
                if(!usedFees.contains(fees.get(period.getIndex()))) {
                    usedFees.add(fees.get(period.getIndex()));
                }
            }
            for(PriceFee fee: usedFees) {
                if(maxPrice == null) {
                    maxPrice = fee;
                    continue;
                }

                if(fee.getElecFee().add(fee.getServiceFee()).compareTo(maxPrice.getElecFee().add(maxPrice.getServiceFee())) > 0) {
                    maxPrice = fee;
                }
            }
        }
        return maxPrice;
    }

    @Override
    public List<PriceInterval> calcIntervals(Price price) {
        PriceProperties properties = price.getProperties();
        List<PriceFee> fees = properties.getFee();
        List<PricePeriod> periods = properties.getPeriod();



        if(price.getType().equals(PriceTypeEnum.Standard)) {
            List<PriceInterval> intervals = new ArrayList<>();
            PriceFee priceFee = fees.get(0);
            PriceInterval priceInterval = new PriceInterval();
            priceInterval.setElecFee(priceFee.getElecFee());
            priceInterval.setServiceFee(priceFee.getServiceFee());
            priceInterval.setAmount(priceFee.getAmount());
            priceInterval.setLabel("00:00-24:00");
            priceInterval.setIsCurrent(true);
            intervals.add(priceInterval);

            return intervals;

        } else {
            List<PriceInterval> intervals = new ArrayList<>();

            for(PricePeriod period: periods) {
                PriceFee priceFee = fees.get(period.getIndex());
                if(intervals.isEmpty()) {
                    PriceInterval priceInterval = new PriceInterval();
                    priceInterval.setLabel(period.getLabel());
                    priceInterval.setName(priceFee.getName());
                    priceInterval.setAmount(priceFee.getAmount());
                    priceInterval.setElecFee(priceFee.getElecFee());
                    priceInterval.setServiceFee(priceFee.getServiceFee());
                    priceInterval.setIndex(period.getIndex());
                    intervals.add(priceInterval);
                    continue;
                }

                PriceInterval lastPriceInterval = intervals.get(intervals.size()-1);

                if(Objects.equals(lastPriceInterval.getIndex(), period.getIndex())) {
                    String lastLabel = lastPriceInterval.getLabel();
                    String currentLabel = period.getLabel();
                    String startHour = lastLabel.substring(0,2);
                    String startMinute = lastLabel.substring(3,5);
                    String endHour = currentLabel.substring(6,8);
                    String endMinute = currentLabel.substring(9,11);
                    String newLabel = String.format("%s:%s-%s:%s", startHour, startMinute, endHour, endMinute);
                    lastPriceInterval.setLabel(newLabel);
                } else {
                    PriceInterval priceInterval = new PriceInterval();
                    priceInterval.setLabel(period.getLabel());
                    priceInterval.setName(priceFee.getName());
                    priceInterval.setAmount(priceFee.getAmount());
                    priceInterval.setElecFee(priceFee.getElecFee());
                    priceInterval.setServiceFee(priceFee.getServiceFee());
                    priceInterval.setIndex(period.getIndex());
                    intervals.add(priceInterval);
                }
            }

            for(PriceInterval interval: intervals) {
                String label = interval.getLabel();
                String startHour = label.substring(0,2);
                String startMinute = label.substring(3,5);
                String endHour = label.substring(6,8);
                String endMinute = label.substring(9,11);

                SimpleDateFormat startSdf = new SimpleDateFormat(String.format("yyyy-MM-dd %s:%s:00", startHour, startMinute ));
                String startDateString = startSdf.format(new Date());

                SimpleDateFormat endSdf = new SimpleDateFormat(String.format("yyyy-MM-dd %s:%s:00", endHour, endMinute ));
                String endDateString = endSdf.format(new Date());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date now = new Date();
                    Date startDate = sdf.parse(startDateString);
                    Date endDate = sdf.parse(endDateString);
                    interval.setIsCurrent(startDate.before(now) && endDate.after(now));
                } catch (Exception e) {
                    log.error("获取计价规则当前价格异常 {}", e.getLocalizedMessage());
                }

            }
            return intervals;
        }
    }

    private Short generateNo() {
        short no = 0;
        Sort sort = Sort.by(Sort.Order.desc("no"));
        List<TbPrice> list = baseRepository.findAll(sort);
        if(!list.isEmpty()) {
            no = list.get(0).getNo();
        }
        no++;
        return no;
    }
}
