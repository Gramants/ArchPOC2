package com.cn29.aac.repositories.api.checknetwork;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkImpl implements CheckNetwork {

    ConnectivityManager connectivityManager;
    public CheckNetworkImpl(ConnectivityManager connectivitymanager) {
        this.connectivityManager=connectivitymanager;
    }

    @Override
    public Boolean isConnectedToInternet() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
