package com.blockchain.mercury.entity;

import com.blockchain.mercury.enums.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactionId")
    private int transactionId;
    @Column(name = "userId")
    private int userId;
    @Column(name = "boughtToken", length = 20)
    private String boughtToken;
    @Column(name = "boughtQuantity")
    private double boughtQuantity;
    @Column(name = "soldToken", length = 20)
    private String soldToken;
    @Column(name = "soldQuantity")
    private double soldQuantity;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "executionDate")
    private Date executionDate;
    @Column(name = "settlementDate")
    private Date settlementDate;

    public Transaction() {

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBoughtQuantity() {
        return boughtQuantity;
    }

    public void setBoughtQuantity(double boughtQuantity) {
        this.boughtQuantity = boughtQuantity;
    }

    public String getBoughtToken() {
        return boughtToken;
    }

    public void setBoughtToken(String boughtToken) {
        this.boughtToken = boughtToken;
    }

    public String getSoldToken() {
        return soldToken;
    }

    public void setSoldToken(String soldToken) {
        this.soldToken = soldToken;
    }

    public double getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(double soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }
}