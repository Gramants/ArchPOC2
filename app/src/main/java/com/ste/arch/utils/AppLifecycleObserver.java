package com.ste.arch.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.widget.Toast;

import com.ste.arch.R;

import javax.inject.Inject;

/**
 * Created by Stefano on 20/11/2017.
 */

public class AppLifecycleObserver implements LifecycleObserver {



    private Toast enterForegroundToast;
    private Toast enterBackgroundToast;
    @Inject
    public AppLifecycleObserver(Context ctx) {


        enterForegroundToast =
                Toast.makeText(ctx, ctx.getApplicationContext().getString(R.string.foreground_message), Toast.LENGTH_SHORT);

        enterBackgroundToast =
                Toast.makeText(ctx, ctx.getApplicationContext().getString(R.string.background_message), Toast.LENGTH_SHORT);

    }



    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        enterBackgroundToast.cancel();
        enterForegroundToast.show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        enterBackgroundToast.show();
        enterForegroundToast.cancel();
    }

}
