package com.obast.charer.data.service;

import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.dao.CameraRepository;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.business.ICameraData;
import com.obast.charer.data.model.TbStation;
import com.obast.charer.data.model.device.TbCharger;
import com.obast.charer.data.model.device.TbPmsc;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.data.model.device.TbCamera;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Camera;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.Pmsc;
import com.obast.charer.model.product.Product;
import com.obast.charer.qo.CameraQueryBo;
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
import java.util.Date;
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
public class CameraDataImpl extends AbstractCommonData<CameraQueryBo>
        implements ICameraData, IJPACommData<Camera, String>, IJPACommonData<Camera, CameraQueryBo, String> {
    
    @Autowired
    private IProductData productData;
    
    private final CameraRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbCamera.class;
    }

    @Override
    public Class<?> getTClass() {
        return Camera.class;
    }

    @Override
    public Paging<Camera> findPage(PageRequest<CameraQueryBo> request) {
        Specification<TbCamera> specification = buildSpecification(request.getData());
        Page<TbCamera> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbCamera> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Camera.class));
    }

    @Override
    public List<Camera> findList(CameraQueryBo queryBo) {
        Specification<TbCamera> specification = buildSpecification(queryBo);
        List<TbCamera> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Camera.class);
    }

    @Override
    public Camera findByDn(String dn) {
        TbCamera entity = baseRepository.findByDn(dn).orElse(null);
        return MapstructUtils.convert(entity, Camera.class);
    }

    @Override
    public List<Camera> findByStationId(String stationId) {
        Specification<TbCamera> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("stationId"), stationId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbCamera> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Camera.class);
    }

    @Override
    public void initData() {
        CameraQueryBo bo = new CameraQueryBo();
        Specification<TbCamera> specification = buildSpecification(bo);
        List<TbCamera> list = baseRepository.findAll(specification);
        for(TbCamera tbCamera: list) {
            tbCamera.setOnline(OnlineStatusEnum.Unknown);
            baseRepository.save(tbCamera);
        }
    }

    @Transactional
    @Override
    public Camera add(Camera dcam) {
        TbCamera bo = new TbCamera();
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

        TbCamera tbCamera = baseRepository.saveAndFlush(bo);

        return MapstructUtils.convert(tbCamera, Camera.class);
    }

    @Transactional
    @Override
    public Camera update(Camera entity) {
        TbCamera bo = baseRepository.findById(entity.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(entity, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Camera.class);
    }

    @Transactional
    @Override
    public void deleteById(String dcamId) {
        baseRepository.deleteById(dcamId);
    }

    public Specification<TbCamera> buildSpecification(CameraQueryBo bo) {
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