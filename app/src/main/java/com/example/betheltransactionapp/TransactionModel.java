package com.example.betheltransactionapp;

import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class TransactionModel {
    private String id;
    private String type; // "Income" or "Expense"
    private double amount;
    private String description;

    @ServerTimestamp
    private Date timestamp; // Firebase will auto-generate the timestamp

    // Empty Constructor for Firebase
    public TransactionModel() {
    }

    // Constructor
    public TransactionModel(String id, String type, double amount, String description, Date timestamp) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

