package com.obast.charer.system.dto;

import lombok.Data;

/**
 * 用户和岗位关联
 *
 * @author Lion Li
 */

@Data
public class SysUserPost {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 岗位ID
     */
    private String postId;

}
