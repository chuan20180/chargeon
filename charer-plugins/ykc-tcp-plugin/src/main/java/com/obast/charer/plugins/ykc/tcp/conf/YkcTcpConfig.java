package com.obast.charer.plugins.ykc.tcp.conf;

import io.vertx.core.net.SocketAddress;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class YkcTcpConfig {

    private String host = "0.0.0.0";

    private int port = 6166;

    /**
     * 服务实例数量(线程数)
     */
    private int instance = Runtime.getRuntime().availableProcessors();

    public SocketAddress createSocketAddress() {
        return SocketAddress.inetSocketAddress(port, host);
    }
}
