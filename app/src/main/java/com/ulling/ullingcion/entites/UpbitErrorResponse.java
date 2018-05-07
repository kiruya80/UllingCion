package com.ulling.ullingcion.entites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpbitErrorResponse {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;

    @SerializedName("trace")
    @Expose
    private String trace;


    public UpbitErrorResponse(int status, String error, String message, String timeStamp,  String trace) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.error = error;
        this.status = status;
        this.trace = trace;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    @Override
    public String toString() {
        return "UpbitErrorResponse{" +
                "message='" + message + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", error='" + error + '\'' +
                ", status='" + status + '\'' +
                ", trace='" + trace + '\'' +
                '}';
    }
}
