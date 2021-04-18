package com.example.pennyless.entities;

import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {

    private long id;
    private String name;
    private double sum;
    private Date addDate;
    private String category;
    private String details;

    public Expense(){}

    public Expense(long id, String name, double sum, Date addDate, String category, String details) {
        this.id = id;
        this.name = name;
        this.sum = sum;
        this.addDate = addDate;
        this.category = category;
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
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
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sum=" + sum +
                ", addDate=" + addDate +
                ", category=" + category +
                ", details='" + details + '\'' +
                '}';
    }
}
