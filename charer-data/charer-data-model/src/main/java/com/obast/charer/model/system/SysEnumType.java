package com.obast.charer.model.system;

import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 字典类型视图对象 sys_dict_type
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysEnumType extends BaseModel implements Id<Long>,Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    private Long id;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

}
