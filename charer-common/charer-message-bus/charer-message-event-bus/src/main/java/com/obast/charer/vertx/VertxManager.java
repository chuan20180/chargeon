package com.obast.charer.vertx;

import io.vertx.core.Vertx;

public class VertxManager {

    private static final Vertx INSTANCE = Vertx.vertx();

    public static Vertx getVertx() {
        return INSTANCE;
    }
}
