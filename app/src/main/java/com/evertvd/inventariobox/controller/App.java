package com.evertvd.inventariobox.controller;


import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import android.app.Application;
import android.support.graphics.drawable.BuildConfig;
import android.util.Log;

import com.evertvd.inventariobox.modelo.MyObjectBox;


/**
 * Created by evertvd on 19/12/2017.
 */
public class App extends Application {

    public static final String TAG = "Inventario Molicon";
    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }

        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }


}