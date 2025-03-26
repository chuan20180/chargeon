package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.PmscRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.IPmscData;
import com.obast.charer.data.model.device.TbDcam;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.data.model.device.TbPmsc;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Dcam;
import com.obast.charer.model.device.Pmsc;
import com.obast.charer.model.product.Product;
import com.obast.charer.qo.PmscQueryBo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩数据服务实现
 */
@Primary
@Service
@RequiredArgsConstructor
public class PmscDataImpl extends AbstractCommonData<PmscQueryBo>
        implements IPmscData, IJPACommData<Pmsc, String>, IJPACommonData<Pmsc, PmscQueryBo, String> {
    
    @Autowired
    private IProductData productData;
    
    private final PmscRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbPmsc.class;
    }

    @Override
    public Class<?> getTClass() {
        return Pmsc.class;
    }

    @Override
    public Paging<Pmsc> findPage(PageRequest<PmscQueryBo> request) {
        Specification<TbPmsc> specification = buildSpecification(request.getData());
        Page<TbPmsc> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbPmsc> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Pmsc.class));
    }

    @Override
    public List<Pmsc> findList(PmscQueryBo queryBo) {
        Specification<TbPmsc> specification = buildSpecification(queryBo);
        List<TbPmsc> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Pmsc.class);
    }

    @Override
    public Pmsc findByDn(String dn) {
        TbPmsc entity = baseRepository.findByDn(dn).orElse(null);
        return MapstructUtils.convert(entity, Pmsc.class);
    }

    @Override
    public List<Pmsc> findByStationId(String stationId) {
        Specification<TbPmsc> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("stationId"), stationId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbPmsc> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Pmsc.class);
    }

    @Override
    public void initData() {
        PmscQueryBo bo = new PmscQueryBo();
        Specification<TbPmsc> specification = buildSpecification(bo);
        List<TbPmsc> list = baseRepository.findAll(specification);
        for(TbPmsc tbPmsc: list) {
            tbPmsc.setOnline(OnlineStatusEnum.Unknown);
            baseRepository.save(tbPmsc);
        }
    }

    @Transactional
    @Override
    public Pmsc add(Pmsc dcam) {

        TbPmsc bo = new TbPmsc();
        ReflectUtil.copyNoNulls(dcam, bo);

        bo.setStatus(EnableStatusEnum.Enabled);

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
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Pmsc.class);
    }

    @Transactional
    @Override
    public Pmsc update(Pmsc entity) {
        TbPmsc bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Pmsc.class);
    }

    @Transactional
    @Override
    public void deleteById(String dcamId) {
        baseRepository.deleteById(dcamId);
    }

    public Specification<TbPmsc> buildSpecification(PmscQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getProductKey())) {
                Predicate predicate = cb.equal(root.get("productKey"), bo.getProductKey());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getStationId())) {
                Predicate predicate = cb.equal(root.get("stationId"), bo.getStationId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getDn())) {
                Predicate predicate = cb.equal(root.get("dn"), bo.getDn());
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


            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}