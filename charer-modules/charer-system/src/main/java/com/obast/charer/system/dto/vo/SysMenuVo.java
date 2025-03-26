package com.obast.charer.system.dto.vo;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.SysMenu;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 菜单权限视图对象 sys_menu
 *
 * @author Michelle.Chung
 */
@Data
@AutoMapper(target = SysMenu.class)
public class SysMenuVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private String id;

    /**
     * 菜单名称
     */
    private I18nField menuName;

    /**
     * 父菜单ID
     */
    private String parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String queryParam;

    /**
     * 是否为外链（0是 1否）
     */
    private String isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private String isCache;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    private EnableStatusEnum status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 创建部门
     */
    private Long createDept;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否租户菜单
     */
    private String isTenant;

    /**
     * 是否代理菜单
     */
    private String isAgent;

    /**
     * 是否合作商菜单
     */
    private String isDealer;


    /**
     * 是否分成菜单
     */
    private String isProfit;

    /**
     * 是否平台角色可用选择菜单
     */
    private String isPlatformApply;

    /**
     * 是否租户可用选择菜单
     */
    private String isTenantApply;

    /**
     * 是否代理可用选择菜单
     */
    private String isAgentApply;

    /**
     * 是否合作商可用选择菜单
     */
    private String isDealerApply;


    /**
     * 子菜单
     */
    private List<SysMenuVo> children = new ArrayList<>();

}
