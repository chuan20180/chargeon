package com.obast.charer.system.dto.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单列表树信息
 *
 * @author Michelle.Chung
 */
@Data
public class MenuTreeSelectVo {

    /**
     * 选中菜单列表
     */
    private List<String> checkedKeys;

    /**
     * 菜单下拉树结构列表
     */
    private List<Tree<String>> menus;

}
