package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.SysMenu;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * 菜单权限表 sys_menu
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_menu")
@AutoMapper(target = SysMenu.class)
@ApiModel(value = "菜单权限表")
public class TbSysMenu extends BaseEntity {

    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @Column(name = "menu_id")
    @ApiModelProperty(value = "菜单ID")
    private String id;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID")
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @Column(name = "menu_name")
    @Type(type = "json")
    private I18nField menuName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @ApiModelProperty(value = "路由参数")
    private String queryParam;

    /**
     * 是否为外链（0是 1否）
     */
    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private String isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @ApiModelProperty(value = "是否缓存（0缓存 1不缓存）")
    private String isCache;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "显示状态（0显示 1隐藏）")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;

    /**
     * 权限字符串
     */
    @ApiModelProperty(value = "权限字符串")
    private String perms;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 是否租户菜单
     */
    @ApiModelProperty(value = "是否租户独有菜单（0否 1是）")
    private String isTenant;

    /**
     * 是否代理菜单
     */
    @ApiModelProperty(value = "是否代理独有菜单（0否 1是）")
    private String isAgent;

    /**
     * 是否合作商菜单
     */
    @ApiModelProperty(value = "是否合作商独有菜单（0否 1是）")
    private String isDealer;

    /**
     * 是否分润菜单
     */
    @ApiModelProperty(value = "是否分润菜单（0否 1是）")
    private String isProfit;

    /**
     * 是否平台角色可用选择菜单
     */
    @ApiModelProperty(value = "是否平台角色可用选择菜单（0否 1是）")
    private String isPlatformApply;

    /**
     * 是否租户可用选择菜单
     */
    @ApiModelProperty(value = "是否租户可用选择菜单（0否 1是）")
    private String isTenantApply;

    /**
     * 是否代理可用选择菜单
     */
    @ApiModelProperty(value = "是否代理可用选择菜单（0否 1是）")
    private String isAgentApply;

    /**
     * 是否合作商可用选择菜单
     */
    @ApiModelProperty(value = "是否合作商可用选择菜单（0否 1是）")
    private String isDealerApply;





}
