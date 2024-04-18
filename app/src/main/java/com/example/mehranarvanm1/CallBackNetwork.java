package com.example.mehranarvanm1;

public interface CallBackNetwork<T> {
    void onResponse(T t);

    void onFail(Throwable throwable, boolean internet);
}
