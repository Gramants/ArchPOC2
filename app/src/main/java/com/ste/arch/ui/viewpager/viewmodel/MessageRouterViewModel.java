package com.ste.arch.ui.viewpager.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


public class MessageRouterViewModel extends ViewModel {
    private MutableLiveData<String> messageContainerA;
    private MutableLiveData<String> messageContainerDummy;
    private MutableLiveData<String> messageContainerC;

    public void init() {
        messageContainerA = new MutableLiveData<>();
        messageContainerDummy = new MutableLiveData<>();
        messageContainerC = new MutableLiveData<>();

    }

    public void sendMessageToDummy(String msg) {
        messageContainerDummy.setValue(msg);
    }

    public void sendMessageToA(String msg) {
        messageContainerA.setValue(msg);
    }

    public void sendMessageToC(String msg) {
        messageContainerC.setValue(msg);
    }

    public LiveData<String> getMessageContainerA() {
        return messageContainerA;
    }

    public LiveData<String> getMessageContainerDummy() {
        return messageContainerDummy;
    }

    public LiveData<String> getMessageContainerC() {
        return messageContainerC;
    }

}
