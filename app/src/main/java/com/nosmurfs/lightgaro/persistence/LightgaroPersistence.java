package com.nosmurfs.lightgaro.persistence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sergio on 26/01/2017.
 */

public class LightgaroPersistence implements Persistence {

    private static final String LIGHTGARO = "lightgaro";

    private static final String UNIQUE_ID_KEY = "UNIQUE_ID_KEY";

    private static final String EMPTY_TEXT = "";

    private final SharedPreferences preferences;

    public LightgaroPersistence(Context context) {
        this.preferences = context.getSharedPreferences(LIGHTGARO, Context.MODE_PRIVATE);
    }

    @Override
    public void saveUniqueId(String uniqueId) {
        saveString(UNIQUE_ID_KEY, uniqueId);
    }

    @Override
    public String getUniqueId() {
        return getString(UNIQUE_ID_KEY);
    }

    @Override
    public boolean hasUniqueId() {
        return preferences.contains(UNIQUE_ID_KEY);
    }

    @Override
    public void removeUniqueId() {
        removeString(UNIQUE_ID_KEY);
    }

    private void saveString(String key, String string) {
        preferences.edit().putString(key, string).apply();
    }

    private String getString(String key) {
        return preferences.getString(key, EMPTY_TEXT);
    }


    private void removeString(String key) {
        preferences.edit().remove(key).apply();
    }
}
