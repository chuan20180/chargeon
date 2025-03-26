package com.obast.charer.system.dto.vo;

import com.obast.charer.model.system.SysEnumType;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;


/**
 * 字典类型视图对象 sys_enum_type
 *
 * @author Michelle.Chung
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysEnumType.class)
public class SysEnumTypeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @ExcelProperty(value = "字典名称")
    private String enumName;

    /**
     * 字典类型
     */
    @ExcelProperty(value = "字典类型")
    private String enumType;


}
