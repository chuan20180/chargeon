package com.obast.charer.system.dto.vo;

import com.obast.charer.common.excel.annotation.ExcelDictFormat;
import com.obast.charer.common.excel.convert.ExcelDictConvert;
import com.obast.charer.model.system.SysDictType;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 字典类型视图对象 sys_dict_type
 *
 * @author Michelle.Chung
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDictType.class)
public class SysDictTypeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @ExcelProperty(value = "字典主键")
    private String id;

    /**
     * 字典名称
     */
    @ExcelProperty(value = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @ExcelProperty(value = "字典类型")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

}
