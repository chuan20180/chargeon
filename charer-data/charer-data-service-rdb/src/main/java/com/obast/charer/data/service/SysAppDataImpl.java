package com.obast.charer.data.service;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ReflectUtil;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.config.id.IdGenerator;
import com.obast.charer.data.dao.SysAppRepository;
import com.obast.charer.data.model.TbSysApp;
import com.obast.charer.data.system.ISysAppData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysApp;
import com.obast.charer.qo.SysAppQueryBo;
import lombok.RequiredArgsConstructor;
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
 * 数据实现接口
 */
@Primary
@Service
@RequiredArgsConstructor
public class SysAppDataImpl extends AbstractCommonData<SysAppQueryBo>
        implements ISysAppData, IJPACommData<SysApp, String>, IJPACommonData<SysApp, SysAppQueryBo, String> {

    private final SysAppRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysApp.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysApp.class;
    }


    @Override
    public Paging<SysApp> findPage(PageRequest<SysAppQueryBo> request) {
        Specification<TbSysApp> specification = buildSpecification(request.getData());
        Page<TbSysApp> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysApp> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, SysApp.class));
    }

    @Override
    public List<SysApp> findList(SysAppQueryBo queryBo) {
        Specification<TbSysApp> specification = buildSpecification(queryBo);
        List<TbSysApp> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysApp.class);
    }

    @Override
    public SysApp findByAppId(String appId) {
        SysAppQueryBo bo = new SysAppQueryBo();
        bo.setAppId(appId);
        Specification<TbSysApp> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("appId"), appId));
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        TbSysApp entity = baseRepository.findOne(specification).orElse(null);
        return MapstructUtils.convert(entity, SysApp.class);
    }

    @Override
    public List<SysApp> findAllByTenantId(String tenantId) {
        SysAppQueryBo bo = new SysAppQueryBo();
        bo.setTenantId(tenantId);
        Specification<TbSysApp> specification = buildSpecification(bo);
        List<TbSysApp> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, SysApp.class);
    }


    @Transactional
    @Override
    public SysApp add(SysApp sysApp) {
        TbSysApp bo = sysApp.to(TbSysApp.class);
        bo.setStatus(EnableStatusEnum.Enabled);

        bo.setAppId(this.generateAppId());

        //生成设备密钥
        String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        int maxPos = chars.length();
        StringBuilder secret = new StringBuilder();
        for (var i = 0; i < 16; i++) {
            secret.append(chars.charAt((int) Math.floor(Math.random() * maxPos)));
        }
        bo.setAppSecret(secret.toString());

        return fillData(baseRepository.saveAndFlush(bo));
    }

    @Transactional
    @Override
    public SysApp update(SysApp sysApp) {
        TbSysApp bo = baseRepository.findById(sysApp.getId()).orElse(null);
        if (bo == null) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION);
        }
        ReflectUtil.copyNoNulls(sysApp, bo);
        return fillData(baseRepository.saveAndFlush(bo));
    }

    private SysApp fillData(TbSysApp tbSysApp) {
        return MapstructUtils.convert(tbSysApp, SysApp.class);
    }

    /**
     * 应用类型
     */
    public Specification<TbSysApp> buildSpecification(SysAppQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();


            if(StringUtils.isNoneBlank(bo.getAppId())) {
                Predicate predicate = cb.equal(root.get("appId"), bo.getAppId());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getAppName())) {
                Predicate predicate = cb.equal(root.get("appName"), bo.getAppName());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getAppType())) {
                Predicate predicate = cb.equal(root.get("appType"), bo.getAppType());
                predicates.add(predicate);
            }

            if(StringUtils.isNoneBlank(bo.getTenantId())) {
                Predicate predicate = cb.equal(root.get("tenantId"), bo.getTenantId());
                predicates.add(predicate);
            }


            return query.where(predicates.toArray(javax.persistence.criteria.Predicate[]::new)).getRestriction();
        };
    }

    private String generateAppId() {
        return IdGenerator.generateAppId();
    }


}
