/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.slf4j.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by yannick on 10/28/15.
 */
public class AndroidLoggerFactory implements ILoggerFactory {
    public static final String TAG = "tag";
    public static final String LEVEL = "level";
    public static final String CONFIG_FILE = "/slf4j-android.properties";
    private final ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();
    private static final String UNDEFINED = "undefined";
    private static final int TAG_MAX_LENGTH = 23;
    private static Context ctx;
    private static String tag;
    private static Level level;
    private static boolean crashlyticsEnabled;
    static ArrayList<Extension> extensions = new ArrayList<Extension>();

    public AndroidLoggerFactory() {
    }

    public static void init(Context ctx, String tag, Level level) {
        AndroidLoggerFactory.ctx = ctx;
        AndroidLoggerFactory.tag = tag;
        if (level == null) {
            boolean debugEnabled = (0 != (ctx.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
            if (debugEnabled) {
                AndroidLoggerFactory.level = Level.DEBUG;
            } else {
                AndroidLoggerFactory.level = Level.INFO;
            }
        } else {
            AndroidLoggerFactory.level = level;
        }
    }

    public synchronized static void enableCrashlytics() {
        if (!crashlyticsEnabled) {
            try {
                extensions.add((Extension) Class.forName("com.kloudtek.slf4j.android.CrashlyticsExtension")
                        .getConstructor(Context.class).newInstance(ctx));
                crashlyticsEnabled = true;
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Level getLevel() {
        return level;
    }

    public static String getTag() {
        return tag;
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
