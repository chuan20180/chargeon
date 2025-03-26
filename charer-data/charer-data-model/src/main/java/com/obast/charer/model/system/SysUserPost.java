package com.obast.charer.model.system;

import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户和岗位关联 sys_user_post
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserPost extends BaseModel implements Id<String>, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 岗位ID
     */
    private String postId;
}
