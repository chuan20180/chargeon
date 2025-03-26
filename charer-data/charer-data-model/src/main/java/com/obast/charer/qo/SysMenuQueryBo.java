package com.obast.charer.qo;

import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;

@Data
public class SysMenuQueryBo {

    private String menuName;

    private EnableStatusEnum status;
}
