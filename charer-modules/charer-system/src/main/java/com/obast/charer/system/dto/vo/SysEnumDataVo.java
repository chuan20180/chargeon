package com.obast.charer.system.dto.vo;

import com.obast.charer.model.system.SysEnumData;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;


/**
 * 字典数据视图对象 sys_enum_data
 *
 * @author Michelle.Chung
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysEnumData.class)
public class SysEnumDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典标签
     */
    @ExcelProperty(value = "字典标签")
    private String enumLabel;

    /**
     * 字典键值
     */
    @ExcelProperty(value = "字典键值")
    private String enumValue;

    /**
     * 字典类型
     */
    @ExcelProperty(value = "字典类型")
    private String enumType;

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


    private String elTagType;


}
