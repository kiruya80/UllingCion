
package com.ulling.ullingcion.entites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ulling.lib.core.entities.QcBaseItem;

public class UpbitPriceResponse extends QcBaseItem {

    @SerializedName("prevClosingPrice")
    @Expose
    private String prevClosingPrice;

    @SerializedName("change")
    @Expose
    private String change;

    @SerializedName("changePrice")
    @Expose
    private String changePrice;

    @SerializedName("changeRate")
    @Expose
    private String changeRate;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("openingPrice")
    @Expose
    private String openingPrice;

    @SerializedName("lowPrice")
    @Expose
    private String lowPrice;

    @SerializedName("timestamp")
    @Expose
    private long timestamp = 0;

    @SerializedName("candleAccTradePrice")
    @Expose
    private String candleAccTradePrice;

    @SerializedName("signedChangePrice")
    @Expose
    private String signedChangePrice;

    @SerializedName("tradePrice")
    @Expose
    private String tradePrice;

    @SerializedName("candleDateTime")
    @Expose
    private String candleDateTime;

    @SerializedName("candleDateTimeKst")
    @Expose
    private String candleDateTimeKst;

    @SerializedName("highPrice")
    @Expose
    private String highPrice;

    @SerializedName("candleAccTradeVolume")
    @Expose
    private String candleAccTradeVolume;

    @SerializedName("signedChangeRate")
    @Expose
    private String signedChangeRate;

    private String logoImgUrl = "";
    private UpbitErrorResponse errorResponse;

    public UpbitPriceResponse() {

    }

    public String getPrevClosingPrice() {
        return prevClosingPrice;
    }

    public void setPrevClosingPrice(String prevClosingPrice) {
        this.prevClosingPrice = prevClosingPrice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCandleAccTradePrice() {
        return candleAccTradePrice;
    }

    public void setCandleAccTradePrice(String candleAccTradePrice) {
        this.candleAccTradePrice = candleAccTradePrice;
    }

    public String getSignedChangePrice() {
        return signedChangePrice;
    }

    public void setSignedChangePrice(String signedChangePrice) {
        this.signedChangePrice = signedChangePrice;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getCandleDateTime() {
        return candleDateTime;
    }

    public void setCandleDateTime(String candleDateTime) {
        this.candleDateTime = candleDateTime;
    }

    public String getCandleDateTimeKst() {
        return candleDateTimeKst;
    }

    public void setCandleDateTimeKst(String candleDateTimeKst) {
        this.candleDateTimeKst = candleDateTimeKst;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getCandleAccTradeVolume() {
        return candleAccTradeVolume;
    }

    public void setCandleAccTradeVolume(String candleAccTradeVolume) {
        this.candleAccTradeVolume = candleAccTradeVolume;
    }

    public String getSignedChangeRate() {
        return signedChangeRate;
    }

    public void setSignedChangeRate(String signedChangeRate) {
        this.signedChangeRate = signedChangeRate;
    }

    public UpbitErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(UpbitErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public String getLogoImgUrl() {
        return logoImgUrl;
    }

    public void setLogoImgUrl(String logoImgUrl) {
        this.logoImgUrl = logoImgUrl;
    }

    @Override
    public String toString() {
        return "UpbitPriceResponse{" +
                "prevClosingPrice='" + prevClosingPrice + '\'' +
                ", change='" + change + '\'' +
                ", changePrice='" + changePrice + '\'' +
                ", changeRate='" + changeRate + '\'' +
                ", code='" + code + '\'' +
                ", openingPrice='" + openingPrice + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                ", timestamp=" + timestamp +
                ", candleAccTradePrice='" + candleAccTradePrice + '\'' +
                ", signedChangePrice='" + signedChangePrice + '\'' +
                ", tradePrice='" + tradePrice + '\'' +
                ", candleDateTime='" + candleDateTime + '\'' +
                ", candleDateTimeKst='" + candleDateTimeKst + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", candleAccTradeVolume='" + candleAccTradeVolume + '\'' +
                ", signedChangeRate='" + signedChangeRate + '\'' +
                ", logoImgUrl='" + logoImgUrl + '\'' +
                ", errorResponse=" + errorResponse +
                '}';
    }
}
