package com.obast.charer.plugins.ykc.tcp.cilent;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.time.Duration;

@Slf4j
public class YkcTcpClient {
    @Getter
    private String id;
    public NetSocket socket;
    @Setter
    private long keepAliveTimeoutMs = Duration.ofSeconds(30).toMillis();
    private volatile long lastKeepAliveTime = System.currentTimeMillis();

    @Setter
    private RecordParser parser;


    @Setter
    private Handler<Buffer> handler;

    public YkcTcpClient(String id) {
        this.id = id;
    }

    public void keepAlive() {
        lastKeepAliveTime = System.currentTimeMillis();
    }

    public boolean isOnline() {
        return System.currentTimeMillis() - lastKeepAliveTime < keepAliveTimeoutMs;
    }

    public void setSocket(NetSocket socket) {
        synchronized (this) {
            if (this.socket != null && this.socket != socket) {
                this.socket.close();
            }

            this.socket = socket
                    .closeHandler(v -> shutdown())
                    .handler(buffer -> {
                        log.info("[消息解析]收到报文 data={}, ip={}", Hex.encodeHexString(buffer.getBytes()), socket.remoteAddress());
                        keepAlive();
                        handler.handle(buffer);
                        if (this.socket != socket) {
                            log.warn("tcp client [{}] memory leak ", socket.remoteAddress());
                            socket.close();
                        }
                    });
        }
    }

    public void shutdown() {
        log.warn("tcp client [{}] disconnect", getId());
        synchronized (this) {
            if (null != socket) {
                execute(socket::close);
                this.socket = null;
            }
        }
    }

    public void sendMessage(Buffer buffer) {
        log.info("[消息解析]发送报文 data={}, ip={}", Hex.encodeHexString(buffer.getBytes()), socket.remoteAddress());
        socket.write(buffer, r -> {
            keepAlive();
            if (r.succeeded()) {
                //log.info("client msg send success:{}", Hex.encodeHexString(buffer.getBytes()));
            } else {
                log.error("client msg send failed", r.cause());
            }
        });
    }

    private void execute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.warn("close tcp client error", e);
        }
    }
}
