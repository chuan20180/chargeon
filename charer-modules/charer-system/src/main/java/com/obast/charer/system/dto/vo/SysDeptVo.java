package com.obast.charer.system.dto.vo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.excel.annotation.ExcelDictFormat;
import com.obast.charer.common.excel.convert.ExcelDictConvert;
import com.obast.charer.model.system.SysDept;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 部门视图对象 sys_dept
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDept.class)
public class SysDeptVo extends BaseDto {
    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private String id;

    /**
     * 父部门id
     */
    private String parentId;

    /**
     * 父部门名称
     */
    private String parentName;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    @ExcelProperty(value = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @ExcelProperty(value = "部门状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    private String status;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

}
