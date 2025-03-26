package com.obast.charer.model;

public interface Owned<T> extends Id<T> {

    String getUid();

    void setUid(String uid);

}
