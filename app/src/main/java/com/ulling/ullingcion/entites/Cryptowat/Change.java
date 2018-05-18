package com.ulling.ullingcion.entites.Cryptowat;

public class Change {
    private String absolute;

    private String percentage;

    public String getAbsolute() {
        return absolute;
    }

    public void setAbsolute(String absolute) {
        this.absolute = absolute;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "\nabsolute= " + absolute +
                "\npercentage= " + percentage + "\n";
    }
}
