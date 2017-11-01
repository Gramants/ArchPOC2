package com.ste.arch.repositories.preferences;


import android.content.SharedPreferences;


public class PersistentStorageProxyImpl implements PersistentStorageProxy {
    private static final String MY_SEARCH = "MY_SEARCH";
    private static final String MY_SEARCH_TEMP = "MY_SEARCH_TEMP";
    private SharedPreferences mPreferences;


    public PersistentStorageProxyImpl(SharedPreferences sharedPreferences) {
        this.mPreferences=sharedPreferences;
    }



    @Override
    public String getSearchString() {
        return mPreferences.getString(MY_SEARCH, "");
    }
    @Override
    public void setSearchString(String searchString) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(MY_SEARCH, searchString);
        editor.apply();
    }

    @Override
    public String getSearchStringTemp() {
        return mPreferences.getString(MY_SEARCH_TEMP, "");
    }
    @Override
    public void setSearchStringTemp(String searchString) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(MY_SEARCH_TEMP, searchString);
        editor.apply();
    }

}
