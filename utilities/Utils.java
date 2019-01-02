package com.example.kioiduncan.errands.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    private static  final  String PREFERENCES_FILE = "settings";

    public static String readSharedSetting(Context context,String settingName, String defaultValue){
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName,defaultValue);
    }
    public static void saveSharedSetting (Context context,String settingName, String settingValue){
        SharedPreferences sharedPref =context.getSharedPreferences(PREFERENCES_FILE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  =sharedPref.edit();
        editor.putString(settingName,settingValue);
        editor.apply();
    }
}
