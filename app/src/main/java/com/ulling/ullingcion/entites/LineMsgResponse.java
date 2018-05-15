package com.ulling.ullingcion.entites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineMsgResponse {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("message")
    @Expose
    private String message;


    public LineMsgResponse(String message) {
        this.message = message;
    }

    public LineMsgResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LineMsgResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
