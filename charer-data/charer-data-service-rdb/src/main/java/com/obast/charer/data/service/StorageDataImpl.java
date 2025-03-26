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
import com.obast.charer.data.system.IStorageData;
import com.obast.charer.data.model.TbStorage;
import com.obast.charer.model.Storage;
import com.obast.charer.qo.StorageQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.obast.charer.data.dao.StorageRepository;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class StorageDataImpl extends AbstractCommonData<StorageQueryBo>
        implements IStorageData, IJPACommData<Storage, String>, IJPACommonData<Storage, StorageQueryBo, String> {

    private final StorageRepository baseRepository;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbStorage.class;
    }

    @Override
    public Class<?> getTClass() {
        return Storage.class;
    }

    @Override
    public Paging<Storage> findPage(PageRequest<StorageQueryBo> request) {
        Specification<TbStorage> specification = buildSpecification(request.getData());
        Page<TbStorage> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbStorage> list = page.getContent();
        long total = page.getTotalElements();
        return new Paging<>(total, MapstructUtils.convert(list, Storage.class));
    }

    @Override
    public List<Storage> findList(StorageQueryBo bo) {
        Specification<TbStorage> specification = buildSpecification(bo);
        List<TbStorage> list = baseRepository.findAll(specification);
        return MapstructUtils.convert(list, Storage.class);
    }

    @Override
    public boolean checkDnUnique(Storage chargerStorage) {
        Specification<TbStorage> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("dn"), chargerStorage.getDn()));
            if(StringUtils.isNoneBlank(chargerStorage.getId())) {
                predicates.add(cb.notEqual(root.get("id"), chargerStorage.getId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbStorage> list = baseRepository.findAll(specification);
        return list.isEmpty();
    }

    @Transactional
    @Override
    public Storage add(Storage chargerGun) {
        TbStorage bo = new TbStorage();
        ReflectUtil.copyNoNulls(chargerGun, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Storage.class);
    }

    @Transactional
    @Override
    public Storage update(Storage chargerGun) {
        TbStorage bo = null;
        if (StringUtils.isNotBlank(chargerGun.getId())) {
            bo = baseRepository.findById(chargerGun.getId()).orElse(null);
        }
        if (bo == null) {
            throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
        }
        ReflectUtil.copyNoNulls(chargerGun, bo);
        return MapstructUtils.convert(baseRepository.saveAndFlush(bo), Storage.class);
    }

    public Specification<TbStorage> buildSpecification(StorageQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNoneBlank(bo.getProductKey())) {
                predicates.add(cb.equal(root.get("productKey"), bo.getProductKey()));
            }
            if(StringUtils.isNoneBlank(bo.getDn())) {
                predicates.add(cb.equal(root.get("dn"), bo.getDn()));
            }
            if(StringUtils.isNoneBlank(bo.getName())) {
                predicates.add(cb.equal(root.get("name"), bo.getName()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
