package com.cy.yangbo.wardrobe.util;

import android.os.Environment;

/**
 * Created by Administrator on 2016/2/23.
 */
public class SDUtil {
    /**
     * 是否有sd卡
     * @return
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
