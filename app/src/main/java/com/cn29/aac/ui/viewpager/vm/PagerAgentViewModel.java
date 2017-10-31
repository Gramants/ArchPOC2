package com.cn29.aac.ui.viewpager.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cn29.aac.SingleLiveEvent;

/**
 * Created by charlesng0209 on 19/6/2017.
 */

public class PagerAgentViewModel extends ViewModel {
    private SingleLiveEvent<String> messageContainerA;
    private SingleLiveEvent<String> messageContainerB;
    private SingleLiveEvent<String> messageContainerC;

    public void init() {
        messageContainerA = new SingleLiveEvent<>();
        //messageContainerA.setValue("Default Message");
        messageContainerB = new SingleLiveEvent<>();
        //messageContainerB.setValue("Default Message");
        messageContainerC = new SingleLiveEvent<>();
        //messageContainerC.setValue("Default Message");
    }

    public void sendMessageToB(String msg) {

        messageContainerB.setValue(msg);
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

    public LiveData<String> getMessageContainerB() {
        return messageContainerB;
    }

    public LiveData<String> getMessageContainerC() {
        return messageContainerC;
    }

}
