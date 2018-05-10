package com.beayeah.gnb.gnbbershka.pojos;

import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

public class Product implements Comparable<Product>{
    private String sku;
    private Double amount;
    private String currency;
    private BigDecimal total;

    public Product(String sku, Double amount, String currency) {
        this.sku = sku;
        this.amount = amount;
        this.currency = currency;
        this.total = new BigDecimal(0);
    }

    public Product(String sku, BigDecimal total, String currency) {
        this.sku = sku;
        this.total = total;
        this.currency = currency;
        this.total = total;
    }

    public Product() {
        this.sku = "";
        this.total = null;
        this.amount = 0.0;
        this.currency = "";
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAmountRoudedAsBankers() {
        BigDecimal dd = new BigDecimal(this.amount).setScale(2, BigDecimal.ROUND_HALF_UP);
        return dd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getSku(), product.getSku());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSku());
    }

    @Override
    public int compareTo(@NonNull Product o) {
        return this.sku.compareTo(o.sku);
    }

}
