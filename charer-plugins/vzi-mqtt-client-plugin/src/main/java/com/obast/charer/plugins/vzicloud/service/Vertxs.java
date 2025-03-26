package com.obast.charer.plugins.vzicloud.service;

import io.vertx.core.Vertx;

public class Vertxs {

    private static final Vertx INSTANCE = Vertx.vertx();

    public static Vertx getVertx() {
        return INSTANCE;
    }
}
