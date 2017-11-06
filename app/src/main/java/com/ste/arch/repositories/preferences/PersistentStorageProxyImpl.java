package com.ste.arch.repositories.preferences;


import android.content.SharedPreferences;


public class PersistentStorageProxyImpl implements PersistentStorageProxy {
    private static final String MY_SEARCH = "MY_SEARCH";
    private SharedPreferences mPreferences;

    public PersistentStorageProxyImpl(SharedPreferences sharedPreferences) {
        this.mPreferences = sharedPreferences;
    }

    @Override
    public String getSearchString() {
        return mPreferences.getString(MY_SEARCH, "square/retrofit");
    }

    @Override
    public void setSearchString(String searchString) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(MY_SEARCH, searchString);
        editor.apply();
    }


}
