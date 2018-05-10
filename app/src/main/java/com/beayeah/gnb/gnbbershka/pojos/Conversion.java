package com.beayeah.gnb.gnbbershka.pojos;

public class Conversion {
    private String from;
    private String to;
    private Double rate;

    public Conversion() {
        this.from = "";
        this.to = "";
        this.rate = 0.0;
    }

    public Conversion(String from, String to, Double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
