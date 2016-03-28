package com.cy.yangbo.wardrobe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

public class SpUtil {
	public static final String SP_MAIN = "main";

	public static <T> void saveToLocal(Context mContext, String name,
			String key, T t) {
		// TODO
		SharedPreferences sp;
		if (name == null)
			sp = getDefaultSharedPreferences(mContext);
		else
			sp = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);

		if (t instanceof Boolean)
			sp.edit().putBoolean(key, (Boolean) t).commit();
		if (t instanceof String)
			sp.edit().putString(key, (String) t).commit();
		if (t instanceof Integer)
			sp.edit().putInt(key, (Integer) t).commit();
		if (t instanceof Float)
			sp.edit().putFloat(key, (Float) t).commit();
		if (t instanceof Long)
			sp.edit().putLong(key, (Long) t).commit();
	}

	/**
	 * 从本地取回数据
	 * 
	 * @param mContext
	 * @param name SharedPreferences名字
	 * 		  null为getDefaultSharedPreferences;
	 * @param key
	 * @param defaultValue 默认值
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> T getFromLocal(Context mContext, String name, String key,
			T defaultValue) {
		SharedPreferences sp;
		if (name == null)
			sp = getDefaultSharedPreferences(mContext);
		else
			sp = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
				Map<String, ?> map = sp.getAll();
		if (map == null)
			return defaultValue;

		if (map.get(key) == null)
			return defaultValue;

		return (T) map.get(key);

	}
    public static boolean clearSp(String name, Context mContext){
        SharedPreferences sp;
        if (name == null)
            sp = getDefaultSharedPreferences(mContext);
        else
            sp = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
        return sp.edit().clear().commit();
    }
    
    public static boolean clearSpOthers(String name,String password,String cookie, Context mContext){
        SharedPreferences sp;
        if (name == null)
            sp = getDefaultSharedPreferences(mContext);
        else
            sp = mContext.getSharedPreferences(name,
					Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(password, "");
        editor.putString(cookie, "");
        
        return editor.commit();
    }
    
	private static SharedPreferences getDefaultSharedPreferences(Context mContext) {
		return PreferenceManager.getDefaultSharedPreferences(mContext);
	}
}
