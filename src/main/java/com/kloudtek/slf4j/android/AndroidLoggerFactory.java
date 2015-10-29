/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.slf4j.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

/**
 * Created by yannick on 10/28/15.
 */
public class AndroidLoggerFactory implements ILoggerFactory {
    public static final String TAG = "tag";
    public static final String LEVEL = "level";
    public static final String CONFIG_FILE = "/slf4j-android.properties";
    private static boolean debugEnabled;
    private final ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();
    private static final String UNDEFINED = "undefined";
    private static final int TAG_MAX_LENGTH = 23;
    private final String tag;
    private final Level level;

    public AndroidLoggerFactory() {
        final Properties properties = new Properties();
        try {
            Executors.newSingleThreadExecutor().submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    final InputStream is = getClass().getResourceAsStream(CONFIG_FILE);
                    if (is != null) {
                        properties.load(is);
                        is.close();
                    }
                    return null;
                }
            }).get();
        } catch (Exception e) {
            Log.e("SLF4J", "Failed to load configuration file slf4j-android.properties: " + e.getMessage(), e);
        }
        Level level = null;
        String tag = properties.getProperty(TAG);
        String levelStr = properties.getProperty(LEVEL);
        if (levelStr != null) {
            try {
                level = Level.valueOf(levelStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                Log.e("SLF4J", "Invalid log level: " + levelStr);
            }
        }
        if (tag != null) {
            this.tag = tag;
        } else {
            this.tag = null;
        }
        if (level != null) {
            this.level = level;
        } else {
            this.level = null;
        }
    }

    public static void init(Context ctx) {
        debugEnabled = (0 != (ctx.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public Logger getLogger(String name) {
        String tag = getTag(name);
        Logger logger = loggerMap.get(tag);
        if (logger == null) {
            Logger newInstance = new AndroidLogger(tag, level, this);
            Logger oldInstance = loggerMap.putIfAbsent(tag, newInstance);
            logger = oldInstance == null ? newInstance : oldInstance;
        }
        return logger;
    }

    private String getTag(String name) {
        if (tag != null) {
            return tag;
        }
        if (name == null) {
            return UNDEFINED;
        }
        int length = name.length();
        if (length <= TAG_MAX_LENGTH) {
            return name;
        } else {
            return name.substring(0, TAG_MAX_LENGTH);
        }
    }
}
