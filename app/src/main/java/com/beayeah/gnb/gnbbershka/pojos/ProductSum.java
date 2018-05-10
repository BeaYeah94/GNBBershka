package com.beayeah.gnb.gnbbershka.pojos;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductSum {
    private ArrayList<Product> product;
    private Double sum;

    public ProductSum() {
        this.product = new ArrayList<Product>();
        this.sum = 0.0;
    }

    public ProductSum(ArrayList<Product> product, Double sum) {
        this.product = product;
        this.sum = sum;
    }

    public BigDecimal getSumRoudedAsBankers() {
        BigDecimal dd = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP);
        return dd;
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
