package com.obast.charer.model.system;

import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginInfo  extends BaseModel implements Id<String>,Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 访问ID
     */
    private String id;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录状态（0成功 1失败）
     */
    private String status;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;


    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    private Date loginTime;
}
