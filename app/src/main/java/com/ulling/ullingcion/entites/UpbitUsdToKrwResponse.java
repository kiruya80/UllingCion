package com.ulling.ullingcion.entites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpbitUsdToKrwResponse {


    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;
    @SerializedName("currencyName")
    @Expose
    private String currencyName;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("recurrenceCount")
    @Expose
    private Integer recurrenceCount;
    @SerializedName("basePrice")
    @Expose
    private Double basePrice;
    @SerializedName("openingPrice")
    @Expose
    private Double openingPrice;
    @SerializedName("highPrice")
    @Expose
    private Double highPrice;
    @SerializedName("lowPrice")
    @Expose
    private Double lowPrice;


    @SerializedName("change")
    @Expose
    private String change;
    @SerializedName("changePrice")
    @Expose
    private Double changePrice;
    @SerializedName("cashBuyingPrice")
    @Expose
    private Double cashBuyingPrice;
    @SerializedName("cashSellingPrice")
    @Expose
    private Double cashSellingPrice;
    @SerializedName("ttBuyingPrice")
    @Expose
    private Double ttBuyingPrice;
    @SerializedName("ttSellingPrice")
    @Expose
    private Double ttSellingPrice;
    @SerializedName("tcBuyingPrice")
    @Expose
    private Double tcBuyingPrice;
    @SerializedName("fcSellingPrice")
    @Expose
    private Double fcSellingPrice;
    @SerializedName("exchangeCommission")
    @Expose
    private Double exchangeCommission;
    @SerializedName("usDollarRate")
    @Expose
    private Double usDollarRate;
    @SerializedName("high52wPrice")
    @Expose
    private Double high52wPrice;
    @SerializedName("high52wDate")
    @Expose
    private String high52wDate;
    @SerializedName("low52wPrice")
    @Expose
    private Double low52wPrice;
    @SerializedName("low52wDate")
    @Expose
    private String low52wDate;
    @SerializedName("currencyUnit")
    @Expose
    private Integer currencyUnit;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("modifiedAt")
    @Expose
    private String modifiedAt;
    @SerializedName("signedChangePrice")
    @Expose
    private Double signedChangePrice;
    @SerializedName("signedChangeRate")
    @Expose
    private Double signedChangeRate;
    @SerializedName("changeRate")
    @Expose
    private Double changeRate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRecurrenceCount() {
        return recurrenceCount;
    }

    public void setRecurrenceCount(Integer recurrenceCount) {
        this.recurrenceCount = recurrenceCount;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(Double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public Double getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(Double changePrice) {
        this.changePrice = changePrice;
    }

    public Double getCashBuyingPrice() {
        return cashBuyingPrice;
    }

    public void setCashBuyingPrice(Double cashBuyingPrice) {
        this.cashBuyingPrice = cashBuyingPrice;
    }

    public Double getCashSellingPrice() {
        return cashSellingPrice;
    }

    public void setCashSellingPrice(Double cashSellingPrice) {
        this.cashSellingPrice = cashSellingPrice;
    }

    public Double getTtBuyingPrice() {
        return ttBuyingPrice;
    }

    public void setTtBuyingPrice(Double ttBuyingPrice) {
        this.ttBuyingPrice = ttBuyingPrice;
    }

    public Double getTtSellingPrice() {
        return ttSellingPrice;
    }

    public void setTtSellingPrice(Double ttSellingPrice) {
        this.ttSellingPrice = ttSellingPrice;
    }

    public Double getTcBuyingPrice() {
        return tcBuyingPrice;
    }

    public void setTcBuyingPrice(Double tcBuyingPrice) {
        this.tcBuyingPrice = tcBuyingPrice;
    }

    public Double getFcSellingPrice() {
        return fcSellingPrice;
    }

    public void setFcSellingPrice(Double fcSellingPrice) {
        this.fcSellingPrice = fcSellingPrice;
    }

    public Double getExchangeCommission() {
        return exchangeCommission;
    }

    public void setExchangeCommission(Double exchangeCommission) {
        this.exchangeCommission = exchangeCommission;
    }

    public Double getUsDollarRate() {
        return usDollarRate;
    }

    public void setUsDollarRate(Double usDollarRate) {
        this.usDollarRate = usDollarRate;
    }

    public Double getHigh52wPrice() {
        return high52wPrice;
    }

    public void setHigh52wPrice(Double high52wPrice) {
        this.high52wPrice = high52wPrice;
    }

    public String getHigh52wDate() {
        return high52wDate;
    }

    public void setHigh52wDate(String high52wDate) {
        this.high52wDate = high52wDate;
    }

    public Double getLow52wPrice() {
        return low52wPrice;
    }

    public void setLow52wPrice(Double low52wPrice) {
        this.low52wPrice = low52wPrice;
    }

    public String getLow52wDate() {
        return low52wDate;
    }

    public void setLow52wDate(String low52wDate) {
        this.low52wDate = low52wDate;
    }

    public Integer getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(Integer currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Double getSignedChangePrice() {
        return signedChangePrice;
    }

    public void setSignedChangePrice(Double signedChangePrice) {
        this.signedChangePrice = signedChangePrice;
    }

    public Double getSignedChangeRate() {
        return signedChangeRate;
    }

    public void setSignedChangeRate(Double signedChangeRate) {
        this.signedChangeRate = signedChangeRate;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    @Override
    public String toString() {
        return "UpbitUsdToKrwResponse{" +
                "code='" + code + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", recurrenceCount=" + recurrenceCount +
                ", basePrice=" + basePrice +
                ", openingPrice=" + openingPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", change='" + change + '\'' +
                ", changePrice=" + changePrice +
                ", cashBuyingPrice=" + cashBuyingPrice +
                ", cashSellingPrice=" + cashSellingPrice +
                ", ttBuyingPrice=" + ttBuyingPrice +
                ", ttSellingPrice=" + ttSellingPrice +
                ", tcBuyingPrice=" + tcBuyingPrice +
                ", fcSellingPrice=" + fcSellingPrice +
                ", exchangeCommission=" + exchangeCommission +
                ", usDollarRate=" + usDollarRate +
                ", high52wPrice=" + high52wPrice +
                ", high52wDate='" + high52wDate + '\'' +
                ", low52wPrice=" + low52wPrice +
                ", low52wDate='" + low52wDate + '\'' +
                ", currencyUnit=" + currencyUnit +
                ", provider='" + provider + '\'' +
                ", timestamp=" + timestamp +
                ", id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", modifiedAt='" + modifiedAt + '\'' +
                ", signedChangePrice=" + signedChangePrice +
                ", signedChangeRate=" + signedChangeRate +
                ", changeRate=" + changeRate +
                '}';
    }
}
