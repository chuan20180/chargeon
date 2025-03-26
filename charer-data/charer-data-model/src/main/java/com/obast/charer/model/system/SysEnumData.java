package com.obast.charer.model.system;

import lombok.Data;

import java.io.Serializable;


/**
 * 字典数据视图对象 sys_dict_data
 *
 * @author Michelle.Chung
 */

@Data
public class SysEnumData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典类型
     */
    private String dictType;


    /**
     * 样式属性（其他样式扩展）
     */
    private String cssClass;

    /**
     * 表格回显样式
     */
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    private String isDefault;



}
