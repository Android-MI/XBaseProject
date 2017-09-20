package com.mi.xbaseproject.common;

public interface Presenter<V> {
    void attachView(V view);
    void detachView();
}
