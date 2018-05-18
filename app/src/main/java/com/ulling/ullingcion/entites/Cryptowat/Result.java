package com.ulling.ullingcion.entites.Cryptowat;

public class Result {
    private Price price;

    private String volume;

    private String volumeQuote;

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolumeQuote() {
        return volumeQuote;
    }

    public void setVolumeQuote(String volumeQuote) {
        this.volumeQuote = volumeQuote;
    }

    @Override
    public String toString() {
        return "price :\n " + price +
                "\n volume= " + volume +
                "\n volumeQuote= " + volumeQuote + "\n";
    }
}
