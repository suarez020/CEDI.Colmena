package com.crystal.colmenacedi.common;

import android.content.Context;
import android.content.SharedPreferences;

//Guardar variable de sesion de la aplicacion
public class SPM {
    private static final String APP_SETTINGS_FILE = "OPCIONES_CERRADO_POR_DETALLE_CRYSTAL";

    private SPM() {}

    private static SharedPreferences getSharedPreferences() {
        return MyApp.getContext()
                .getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setString(String dataLabel, String dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.commit();
    }

    public static String getString(String dataLabel) {
        return getSharedPreferences().getString(dataLabel, null);
    }

    public static void setBoolean(String dataLabel, boolean dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(dataLabel, dataValue);
        editor.commit();
    }

    public static boolean getBoolean(String dataLabel) {
        return getSharedPreferences().getBoolean(dataLabel,false);
    }

    public static void setInt(String dataLabel, int dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(dataLabel, dataValue);
        editor.commit();
    }

    public static int getInt(String dataLabel) {
        return getSharedPreferences().getInt(dataLabel,0);
    }
}
