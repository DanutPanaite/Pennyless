package com.example.pennyless.entities;

import java.io.Serializable;

public class Income implements Serializable {

    private long id;
    private double sum;
    private String category;
    private String details;

    public Income(){}

    public Income(long id, double sum, String category, String details) {
        this.id = id;
        this.sum = sum;
        this.category = category;
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", sum=" + sum +
                ", category='" + category + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
