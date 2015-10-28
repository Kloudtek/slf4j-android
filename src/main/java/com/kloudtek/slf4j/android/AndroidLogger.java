/*
 * Copyright (c) 2015 Kloudtek Ltd
 */

package com.kloudtek.slf4j.android;

import android.util.Log;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * Created by yannick on 10/28/15.
 */
public class AndroidLogger extends MarkerIgnoringBase {
    private static final long serialVersionUID = 742140848989662299L;
    private final Level level;
    private final AndroidLoggerFactory factory;

    public AndroidLogger(final String tag, final Level level, final AndroidLoggerFactory factory) {
        name = tag;
        this.level = level;
        this.factory = factory;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTraceEnabled() {
        return isLoggable(Log.VERBOSE);
    }

    /**
     * {@inheritDoc}
     */
    public void trace(String msg) {
        log(Log.VERBOSE, msg, (Throwable) null);
    }

    /**
     * {@inheritDoc}
     */
    public void trace(String format, Object arg) {
        log(Log.VERBOSE, format, arg);
    }

    /**
     * {@inheritDoc}
     */
    public void trace(String format, Object arg1, Object arg2) {
        log(Log.VERBOSE, format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    public void trace(String format, Object... argArray) {
        log(Log.VERBOSE, format, argArray);
    }

    /**
     * {@inheritDoc}
     */
    public void trace(String msg, Throwable t) {
        log(Log.VERBOSE, msg, t);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isDebugEnabled() {
        return isLoggable(Log.DEBUG);
    }

    /**
     * {@inheritDoc}
     */
    public void debug(String msg) {
        log(Log.DEBUG, msg, (Throwable) null);
    }

    /**
     * {@inheritDoc}
     */
    public void debug(String format, Object arg) {
        log(Log.DEBUG, format, arg);
    }

    /**
     * {@inheritDoc}
     */
    public void debug(String format, Object arg1, Object arg2) {
        log(Log.DEBUG, format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    public void debug(String format, Object... argArray) {
        log(Log.DEBUG, format, argArray);
    }

    /**
     * {@inheritDoc}
     */
    public void debug(String msg, Throwable t) {
        log(Log.VERBOSE, msg, t);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isInfoEnabled() {
        return isLoggable(Log.INFO);
    }

    /**
     * {@inheritDoc}
     */
    public void info(String msg) {
        log(Log.INFO, msg, (Throwable) null);
    }

    /**
     * {@inheritDoc}
     */
    public void info(String format, Object arg) {
        log(Log.INFO, format, arg);
    }

    /**
     * {@inheritDoc}
     */
    public void info(String format, Object arg1, Object arg2) {
        log(Log.INFO, format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    public void info(String format, Object... argArray) {
        log(Log.INFO, format, argArray);
    }

    /**
     * {@inheritDoc}
     */
    public void info(String msg, Throwable t) {
        log(Log.INFO, msg, t);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isWarnEnabled() {
        return isLoggable(Log.WARN);
    }

    /**
     * {@inheritDoc}
     */
    public void warn(String msg) {
        log(Log.WARN, msg, (Throwable) null);
    }

    /**
     * {@inheritDoc}
     */
    public void warn(String format, Object arg) {
        log(Log.WARN, format, arg);
    }

    /**
     * {@inheritDoc}
     */
    public void warn(String format, Object arg1, Object arg2) {
        log(Log.WARN, format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    public void warn(String format, Object... argArray) {
        log(Log.WARN, format, argArray);
    }

    /**
     * {@inheritDoc}
     */
    public void warn(String msg, Throwable t) {
        log(Log.WARN, msg, t);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isErrorEnabled() {
        return isLoggable(Log.ERROR);
    }

    /**
     * {@inheritDoc}
     */
    public void error(String msg) {
        log(Log.ERROR, msg, (Throwable) null);
    }

    /**
     * {@inheritDoc}
     */
    public void error(String format, Object arg) {
        log(Log.ERROR, format, arg);
    }

    /**
     * {@inheritDoc}
     */
    public void error(String format, Object arg1, Object arg2) {
        log(Log.ERROR, format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    public void error(String format, Object... argArray) {
        log(Log.ERROR, format, argArray);
    }

    /**
     * {@inheritDoc}
     */
    public void error(String msg, Throwable t) {
        log(Log.ERROR, msg, t);
    }

    private void log(int priority, String format, Object... argArray) {
        if (isLoggable(priority)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            doLog(priority, ft.getMessage(), ft.getThrowable());
        }
    }

    private void log(int priority, String message, Throwable throwable) {
        if (isLoggable(priority)) {
            doLog(priority, message, throwable);
        }
    }

    private boolean isLoggable(int priority) {
        if (level != null) {
            if (level == Level.AUTO) {
                return factory.isDebugEnabled() || Log.INFO <= priority;
            } else {
                return level.getValue() <= priority;
            }
        } else {
            return Log.isLoggable(name, priority);
        }
    }

    private void doLog(int priority, String message, Throwable throwable) {
        if (throwable != null) {
            message += '\n' + Log.getStackTraceString(throwable);
        }
        Log.println(priority, name, message);
    }
}
