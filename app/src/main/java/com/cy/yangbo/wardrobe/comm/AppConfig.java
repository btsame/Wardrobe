package com.cy.yangbo.wardrobe.comm;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/2/24.
 */
public class AppConfig {

    public static final String BASE_DIRECTORY;
    public static final String PHOTO_DIRECTORY;

    public static final boolean DEBUG = true;

    public static final String BAIDU_WEATHER_ENDPOINT = "http://api.map.baidu.com/";

    public static final String BAIDU_APP_KEY = "6tYzTvGZSOpYB5Oc2YGGOKt8";

    static {
        BASE_DIRECTORY = Environment.getExternalStorageDirectory() + "/Wardrobe";
        PHOTO_DIRECTORY = BASE_DIRECTORY + "/picture";
        File file = new File(PHOTO_DIRECTORY);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }

    }
}
