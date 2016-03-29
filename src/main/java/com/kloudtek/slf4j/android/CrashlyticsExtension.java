package com.kloudtek.slf4j.android;

import android.content.Context;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by yannick on 29/3/16.
 */
public class CrashlyticsExtension implements Extension {
    public CrashlyticsExtension(Context context) {
        Fabric.with(context, new Crashlytics());
    }

    @Override
    public void log(int priority, String tag, String message, Throwable throwable) {
        Crashlytics.log(priority,tag,message);
        if( throwable != null ) {
            Crashlytics.logException(throwable);
        }
    }
}
