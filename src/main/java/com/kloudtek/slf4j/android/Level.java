/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.slf4j.android;

import android.util.Log;

/**
 * Created by yannick on 10/28/15.
 */
public enum Level {
    AUTO(0), VERBOSE(Log.VERBOSE), DEBUG(Log.DEBUG), INFO(Log.INFO), WARN(Log.WARN), ERROR(Log.ERROR), ASSERT(Log.ASSERT);
    private int value;

    Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
