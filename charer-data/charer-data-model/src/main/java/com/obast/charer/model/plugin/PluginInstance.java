package com.obast.charer.model.plugin;

import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

/**
 * 插件实例
 *
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PluginInstance extends BaseModel implements Id<String>, Serializable {

    private String id;

    /**
     * 插件主程序id
     */
    private String mainId;

    /**
     * 插件id
     */
    private String pluginId;

    /**
     * 插件主程序所在ip
     */
    private String ip;

    /**
     * 插件主程序端口
     */
    private int port;

    /**
     * 心跳时间
     * 心路时间超过30秒需要剔除
     */
    private Long heartbeatAt;

}
