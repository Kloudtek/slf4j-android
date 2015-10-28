/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package org.slf4j.impl;

import com.kloudtek.slf4j.android.AndroidLoggerFactory;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * Created by yannick on 10/28/15.
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {
    public static String REQUESTED_API_VERSION = "1.7.12";
    public static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
    private static final String loggerFactoryClassStr = AndroidLoggerFactory.class.getName();
    private final ILoggerFactory factory;

    public static StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    private StaticLoggerBinder() {
        factory = new AndroidLoggerFactory();
    }

    public ILoggerFactory getLoggerFactory() {
        return factory;
    }

    public String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr;
    }
}
