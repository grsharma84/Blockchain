package com.blockchain.mercury.model;

import com.blockchain.mercury.enums.Currency;

import java.io.Serializable;

public class KafkaMessage implements Serializable {

    private int userId;
    private int transactionId;
    private Currency broughtToken;
    private double broughtQuantity;
    private Currency soldToken;
    private double soldQuantity;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Currency getBroughtToken() {
        return broughtToken;
    }

    public void setBroughtToken(Currency broughtToken) {
        this.broughtToken = broughtToken;
    }

    public double getBroughtQuantity() {
        return broughtQuantity;
    }

    public void setBroughtQuantity(double broughtQuantity) {
        this.broughtQuantity = broughtQuantity;
    }

    public Currency getSoldToken() {
        return soldToken;
    }

    public void setSoldToken(Currency soldToken) {
        this.soldToken = soldToken;
    }

    public double getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(double soldQuantity) {
        this.soldQuantity = soldQuantity;
    }
}