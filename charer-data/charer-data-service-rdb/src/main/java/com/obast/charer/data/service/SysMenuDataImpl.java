package com.obast.charer.data.service;

import cn.hutool.core.collection.CollectionUtil;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.constant.UserConstants;
import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.AbstractCommonData;
import com.obast.charer.data.IJPACommData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.data.dao.SysMenuRepository;
import com.obast.charer.data.dao.SysRoleMenuRepository;
import com.obast.charer.data.dao.SysUserRoleRepository;
import com.obast.charer.data.model.TbSysMenu;
import com.obast.charer.data.model.TbSysRoleMenu;
import com.obast.charer.data.model.TbSysUserRole;
import com.obast.charer.data.system.ISysMenuData;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.system.SysMenu;
import com.obast.charer.qo.SysMenuQueryBo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class SysMenuDataImpl extends AbstractCommonData<SysMenuQueryBo>
        implements ISysMenuData, IJPACommData<SysMenu, String>, IJPACommonData<SysMenu, SysMenuQueryBo, String> {

    private final SysMenuRepository baseRepository;

    private final SysMenuRepository sysMenuRepository;
    private final SysUserRoleRepository sysUserRoleRepository;

    private final SysRoleMenuRepository sysRoleMenuRepository;

    private final CharerProperties.TenantProperties tenantProperties;

    private final CharerProperties.AgentProperties agentProperties;

    private final CharerProperties.DealerProperties dealerProperties;

    private final CharerProperties.ProfitProperties profitProperties;

    @Override
    public JpaRepository<?,?> getBaseRepository() {
        return baseRepository;
    }

    @Override
    public Class<?> getJpaRepositoryClass() {
        return TbSysMenu.class;
    }

    @Override
    public Class<?> getTClass() {
        return SysMenu.class;
    }

    @Override
    public Paging<SysMenu> findPage(PageRequest<SysMenuQueryBo> request) {
        Specification<TbSysMenu> specification = buildSpecification(request.getData());
        Page<TbSysMenu> page = baseRepository.findAll(specification, processPageSort(request));
        List<TbSysMenu> list = page.getContent();
        long total = page.getTotalElements();

        List<SysMenu> newList = new ArrayList<>();
        for(TbSysMenu tbStation: list) {
            newList.add(fillData(tbStation));
        }
        return new Paging<>(total, newList);
    }

    @Override
    public List<SysMenu> findList(SysMenuQueryBo queryBo) {
        Specification<TbSysMenu> specification = buildSpecification(queryBo);
        Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
        List<TbSysMenu> list = baseRepository.findAll(specification, sort);
        List<SysMenu> newList = new ArrayList<>();
        for(TbSysMenu tbSysMenu: list) {
            newList.add(fillData(tbSysMenu));
        }
        return newList;
    }

    @Override
    public List<SysMenu> findAllList(SysMenuQueryBo bo) {
        Specification<TbSysMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNoneBlank(bo.getMenuName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("menuName"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getMenuName() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
        return MapstructUtils.convert(baseRepository.findAll(specification, sort), SysMenu.class);
    }

    @Override
    public List<SysMenu> findPlatformList(SysMenuQueryBo bo) {
        Specification<TbSysMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNoneBlank(bo.getMenuName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("menuName"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getMenuName() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }
            Predicate predicate = cb.equal(root.get("isPlatformApply"), 1);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
        return MapstructUtils.convert(baseRepository.findAll(specification, sort), SysMenu.class);
    }

    @Override
    public List<SysMenu> findTenantList(SysMenuQueryBo bo) {
        Specification<TbSysMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNoneBlank(bo.getMenuName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("menuName"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getMenuName() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }
            Predicate predicate = cb.equal(root.get("isTenantApply"), 1);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
        return MapstructUtils.convert(baseRepository.findAll(specification, sort), SysMenu.class);
    }

    @Override
    public List<SysMenu> findAgentList(SysMenuQueryBo bo) {
        Specification<TbSysMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNoneBlank(bo.getMenuName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("menuName"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getMenuName() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }
            Predicate predicate = cb.equal(root.get("isAgentApply"), 1);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
        return MapstructUtils.convert(baseRepository.findAll(specification, sort), SysMenu.class);
    }

    @Override
    public List<SysMenu> findDealerList(SysMenuQueryBo bo) {
        Specification<TbSysMenu> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.isNoneBlank(bo.getMenuName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("menuName"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getMenuName() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }
            Predicate predicate = cb.equal(root.get("isDealerApply"), 1);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
        return MapstructUtils.convert(baseRepository.findAll(specification, sort), SysMenu.class);
    }


    private SysMenu fillData(TbSysMenu tbSysMenu) {
        return MapstructUtils.convert(tbSysMenu, SysMenu.class);
    }



    @Override
    public SysMenu save(SysMenu data) {
        baseRepository.save(MapstructUtils.convert(data, TbSysMenu.class));
        return data;
    }

    @Override
    public void batchSave(List<SysMenu> data) {
        List<TbSysMenu> tbSysMenus = data.stream().map(e -> MapstructUtils.convert(e, TbSysMenu.class)).collect(Collectors.toList());
        baseRepository.saveAll(tbSysMenus);
    }

    public Specification<TbSysMenu> buildSpecification(SysMenuQueryBo bo) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNoneBlank(bo.getMenuName())) {
                Locale locale = LocaleContextHolder.getLocale();
                String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
                Predicate namePredicate = cb.like(cb.function("JSON_EXTRACT", String.class, root.get("menuName"), cb.literal(String.format("$.%s", requestLang))), "%" + bo.getMenuName() + "%");
                predicates.add(namePredicate);
            }

            if(bo.getStatus() != null) {
                Predicate statusPredicate = cb.equal(root.get("status"), bo.getStatus().getCode());
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    @Override
    public void deleteById(String id) {
        baseRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(Collection<String> ids) {
        baseRepository.deleteAllByIdInBatch(ids);
    }



    @Override
    public List<String> selectMenuPermsByUserId(String userId) {

        Specification<TbSysUserRole> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("userId"), userId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysUserRole> tbSysUserRoles = sysUserRoleRepository.findAll(specification);
        if(!tbSysUserRoles.isEmpty()) {
            List<String> roleIds = tbSysUserRoles.stream().map(TbSysUserRole::getRoleId).collect(Collectors.toList());

            Specification<TbSysRoleMenu> roleMenuSpecification = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                Predicate predicate = root.get("roleId").in(roleIds);
                predicates.add(predicate);
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            };
            List<TbSysRoleMenu> tbSysRoleMenus = sysRoleMenuRepository.findAll(roleMenuSpecification);

            if(!tbSysRoleMenus.isEmpty()) {
                List<String> menuIds = tbSysRoleMenus.stream().map(TbSysRoleMenu::getMenuId).collect(Collectors.toList());

                Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    Predicate predicate = root.get("id").in(menuIds);
                    predicates.add(predicate);

                    Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
                    predicates.add(statusPredicate);
                    return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
                };
                List<TbSysMenu> tbMenus = sysMenuRepository.findAll(menuSpecification);

                if(!tbMenus.isEmpty()) {
                    return tbMenus.stream().map(TbSysMenu::getPerms).collect(Collectors.toList());
                }
            }
        }

        return new ArrayList<>();
    }

    @Override
    public List<String> selectMenuPermsByRoleId(String roleId) {

        Specification<TbSysRoleMenu> roleMenuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("roleId"), roleId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysRoleMenu> tbSysRoleMenus = sysRoleMenuRepository.findAll(roleMenuSpecification);

        if(!tbSysRoleMenus.isEmpty()) {
            List<String> menuIds = tbSysRoleMenus.stream().map(TbSysRoleMenu::getMenuId).collect(Collectors.toList());
            Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                Predicate predicate = root.get("id").in(menuIds);
                predicates.add(predicate);
                Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
                predicates.add(statusPredicate);
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            };
            List<TbSysMenu> tbMenus = sysMenuRepository.findAll(menuSpecification);

            if(!tbMenus.isEmpty()) {
                return tbMenus.stream().map(TbSysMenu::getPerms).collect(Collectors.toList());
            }
        }

        return new ArrayList<>();
    }

    @Override
    public List<SysMenu> selectMenuTreeAll() {

        Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
            predicates.add(statusPredicate);

            Predicate predicate = root.get("menuType").in(List.of(UserConstants.TYPE_DIR, UserConstants.TYPE_MENU));
            predicates.add(predicate);

            if(!tenantProperties.getEnable()) {
                predicates.add(cb.equal(root.get("isTenant"), 0));
            }

            if(!agentProperties.getEnable()) {
                predicates.add(cb.equal(root.get("isAgent"), 0));
                predicates.add(cb.equal(root.get("isDealer"), 0));
            }

            if(!dealerProperties.getEnable()) {
                predicates.add(cb.equal(root.get("isDealer"), 0));
            }

            if(!profitProperties.getEnabled()) {
                predicates.add(cb.equal(root.get("isProfit"), 0));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };

        Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
        List<TbSysMenu> tbMenus = sysMenuRepository.findAll(menuSpecification, sort);

        return MapstructUtils.convert(tbMenus,SysMenu.class);
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId(String userId) {
        Specification<TbSysUserRole> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate predicate = cb.equal(root.get("userId"), userId);
            predicates.add(predicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysUserRole> tbSysUserRoles = sysUserRoleRepository.findAll(specification);
        if(!tbSysUserRoles.isEmpty()) {
            List<String> roleIds = tbSysUserRoles.stream().map(TbSysUserRole::getRoleId).collect(Collectors.toList());

            Specification<TbSysRoleMenu> roleMenuSpecification = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                Predicate predicate = root.get("roleId").in(roleIds);
                predicates.add(predicate);
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            };
            List<TbSysRoleMenu> tbSysRoleMenus = sysRoleMenuRepository.findAll(roleMenuSpecification);

            if(!tbSysRoleMenus.isEmpty()) {
                List<String> menuIds = tbSysRoleMenus.stream().map(TbSysRoleMenu::getMenuId).collect(Collectors.toList());

                Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    Predicate predicate = root.get("id").in(menuIds);
                    predicates.add(predicate);

                    Predicate statusPredicate = cb.equal(root.get("status"), EnableStatusEnum.Enabled.getCode());
                    predicates.add(statusPredicate);

                    if(!tenantProperties.getEnable()) {
                        predicates.add(cb.equal(root.get("isTenant"), 0));
                    }

                    if(!agentProperties.getEnable()) {
                        predicates.add(cb.equal(root.get("isAgent"), 0));
                        predicates.add(cb.equal(root.get("isDealer"), 0));
                    }

                    if(!dealerProperties.getEnable()) {
                        predicates.add(cb.equal(root.get("isDealer"), 0));
                    }

                    if(!profitProperties.getEnabled()) {
                        predicates.add(cb.equal(root.get("isProfit"), 0));
                    }

                    return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
                };

                Sort sort = Sort.by(Sort.Order.asc("parentId"), Sort.Order.asc("orderNum"));
                List<TbSysMenu> tbMenus = sysMenuRepository.findAll(menuSpecification, sort);

                return MapstructUtils.convert(tbMenus, SysMenu.class);
            }
        }

        return new ArrayList<>();

    }

    @Override
    public boolean hasChildByMenuId(String menuId) {

        Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("parentId"), menuId);
            predicates.add(statusPredicate);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return sysMenuRepository.count(menuSpecification) > 0;
    }

    @Override
    public boolean checkMenuNameUnique(SysMenu menu) {

        Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = cb.equal(root.get("parentId"), menu.getParentId());
            predicates.add(statusPredicate);

            Predicate namePredicate = cb.equal(root.get("menuName"), menu.getMenuName());
            predicates.add(namePredicate);

            if(StringUtils.isNoneBlank(menu.getId())) {
                Predicate menuIdPredicate = cb.notEqual(root.get("menuId"), menu.getId());
                predicates.add(menuIdPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return sysMenuRepository.count(menuSpecification) > 0;
    }

    @Override
    public List<String> selectParentIdByMenuIds(List<String> menuIds) {

        Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate statusPredicate = root.get("parentId").in(menuIds);
            predicates.add(statusPredicate);

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysMenu> tbSysMenus = sysMenuRepository.findAll(menuSpecification);
        return tbSysMenus.stream().map(TbSysMenu::getParentId).collect(Collectors.toList());
    }

    @Override
    public List<String> findByMenuIdListAndNotParentIdList(List<String> menuIds, List<String> parentIds) {


        Specification<TbSysMenu> menuSpecification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate predicate = root.get("id").in(menuIds);
            predicates.add(predicate);
            
            
            if(CollectionUtil.isNotEmpty(parentIds)) {
                Predicate statusPredicate = cb.not(root.get("id").in(parentIds));
                predicates.add(statusPredicate);
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        List<TbSysMenu> tbSysMenus = sysMenuRepository.findAll(menuSpecification);
        return tbSysMenus.stream().map(TbSysMenu::getParentId).collect(Collectors.toList());
    }
}
