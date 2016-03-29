package com.kloudtek.slf4j.android;

/**
 * Created by yannick on 29/3/16.
 */
public interface Extension {
    void log(int priority, String tag, String message, Throwable throwable);
}
