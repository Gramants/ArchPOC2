package com.cn29.aac.entities;

public class NetworkErrorObject {

    private int errorCode;
    private String errorMsg;

    public NetworkErrorObject(int errorCode, String errorMsg, String endpointOrigin) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.endpointOrigin = endpointOrigin;
    }

    private String endpointOrigin;



    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getEndpointOrigin() {
        return endpointOrigin;
    }

    public void setEndpointOrigin(String endpointOrigin) {
        this.endpointOrigin = endpointOrigin;
    }
}
