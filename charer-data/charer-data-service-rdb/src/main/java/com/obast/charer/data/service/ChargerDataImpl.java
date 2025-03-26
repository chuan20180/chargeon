package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IChargerGunData;
import com.obast.charer.data.dao.ChargerGunRepository;
import com.obast.charer.data.dao.ChargerRepository;
import com.obast.charer.data.model.device.TbCharger;
import com.obast.charer.data.model.device.TbChargerGun;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.enums.ChargerGunStateEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.product.Product;
import com.obast.charer.qo.ChargerQueryBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩数据服务实现
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class ChargerDataImpl extends AbstractCommonData<ChargerQueryBo>
        implements IChargerData, IJPACommData<Charger, String>, IJPACommonData<Charger, ChargerQueryBo, String> {


    @Autowired
    private IProductData productData;

    @Autowired
    private IChargerGunData chargerGunData;

    private final ChargerRepository baseRepository;

    private final ChargerGunRepository chargerGunRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbCharger.class;
    }

    @Override
    public Class<?> getTClass() {
        return Charger.class;
    }

    @Override
    public Paging<Charger> findPage(PageRequest<ChargerQueryBo> request) {
        Specification<TbCharger> specification = buildSpecification(request.getData());
        Page<TbCharger> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbCharger> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Charger.class));
    }

    @Override
    public List<Charger> findList(ChargerQueryBo queryBo) {
        Specification<TbCharger> specification = buildSpecification(queryBo);
        List<TbCharger> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Charger.class);
    }

    @Override
    public Charger findByDn(String dn) {
        TbCharger entity = baseRepository.findByDn(dn).orElse(null);
        return MapstructUtils.convert(entity, Charger.class);
    }

    @Override
    public List<Charger> findByPriceId(String priceId) {
        ChargerQueryBo bo = new ChargerQueryBo();
        bo.setPriceId(priceId);
        Specification<TbCharger> specification = buildSpecification(bo);
        List<TbCharger> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Charger.class);
    }

    @Override
    public List<Charger> findByStationId(String stationId) {
        Specification<TbCharger> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("stationId"), stationId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbCharger> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Charger.class);
    }

    @Override
    public List<Charger> findAvailableByStationId(String stationId) {
        Specification<TbCharger> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate stationIdPredicate = cb.equal(root.get("stationId"), stationId);
            predicates.add(stationIdPredicate);
            Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbCharger> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Charger.class);
    }

    @Override
    public Long findCount(ChargerQueryBo queryBo) {
        Specification<TbCharger> specification = buildSpecification(queryBo);
        return baseRepository.count(specification);
    }

    @Override
    public void initData() {
        ChargerQueryBo bo = new ChargerQueryBo();
        Specification<TbCharger> specification = buildSpecification(bo);
        List<TbCharger> list = baseRepository.findAll(specification);
        for(TbCharger tbCharger: list) {
            tbCharger.setOnline(OnlineStatusEnum.Unknown);
            baseRepository.save(tbCharger);
        }
    }

    @Transactional
    @Override
    public Charger add(Charger charger) {
        TbCharger bo = new TbCharger();
        ReflectUtil.copyNoNulls(charger, bo);

        if(bo.getStatus() == null) {
            bo.setStatus(EnableStatusEnum.Enabled);
        }

        Product product = productData.findByProductKey(bo.getProductKey());
        if (product == null) {
            throw new BizException(ErrCode.PRODUCT_NOT_FOUND);
        }

        //生成设备密钥
        String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        int maxPos = chars.length();
        StringBuilder secret = new StringBuilder();
        for (var i = 0; i < 16; i++) {
            secret.append(chars.charAt((int) Math.floor(Math.random() * maxPos)));
        }
        bo.setSecret(secret.toString());

        bo.setOnline(OnlineStatusEnum.Unknown);

        Product.Charger productCharger = product.getCharger();
        if(productCharger == null) {
            throw new BizException(ErrCode.PRODUCT_PROPERTIES_NOT_FOUND);
        }

        if(productCharger.getLowBalance() == null || productCharger.getLowBalance().compareTo(new BigDecimal(0)) == 0) {
            throw new BizException(ErrCode.PRODUCT_PROPERTIES_LOW_BALANCE_NOT_SETTING);
        }

        bo.setLowBalance(productCharger.getLowBalance());

        TbCharger tbCharger = baseRepository.saveAndFlush(bo);

        Integer gunQty = productCharger.getQty();
        if(gunQty == null || gunQty <= 0) {
            throw new BizException(ErrCode.PRODUCT_PROPERTIES_CHARGER_GUN_QTY_NOT_SETTING);
        }

        for(int i = 1; i<= gunQty; i++) {
            ChargerGun chargerGun = new ChargerGun();
            chargerGun.setChargerId(tbCharger.getId());
            chargerGun.setNo(String.format("%02d", i));
            chargerGun.setSpeed(productCharger.getSpeed());
            chargerGun.setPower(productCharger.getPower());
            chargerGun.setCurrent(productCharger.getCurrent());
            chargerGun.setState(ChargerGunStateEnum.Unknow);
            chargerGunData.save(chargerGun);
        }

        return MapstructUtils.convert(tbCharger, Charger.class);
    }

    @Transactional
    @Override
    public Charger update(Charger charger) {
        TbCharger bo = baseRepository.findById(charger.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(charger, bo);

        log.debug("要更新的值: {}", bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Charger.class);
    }

    @Transactional
    @Override
    public void deleteById(String chargerId) {
        baseRepository.deleteById(chargerId);
        List<TbChargerGun> chargerGuns = chargerGunRepository.findByChargerId(chargerId);
        if(!chargerGuns.isEmpty()) {
            List<String> chargerGunIds = chargerGuns.stream().map(TbChargerGun::getId).collect(Collectors.toList());
            chargerGunRepository.deleteAllById(chargerGunIds);
        }
    }

    public Specification<TbCharger> buildSpecification(ChargerQueryBo bo) {
        return (root, query, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getProductKey())) {
                Predicate predicate = cb.equal(root.get("productKey"), bo.getProductKey());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getStationId())) {
                Predicate predicate = cb.equal(root.get("stationId"), bo.getStationId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getPriceId())) {
                Predicate predicate = cb.equal(root.get("priceId"), bo.getPriceId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getName())) {
                Predicate predicate = cb.equal(root.get("name"), bo.getName());
                predicates.add(predicate);
            }

            if(bo.getOnline() != null) {
                Predicate predicate = cb.equal(root.get("online"), bo.getOnline().getCode());
                predicates.add(predicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
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
}