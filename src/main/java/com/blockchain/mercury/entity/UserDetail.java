package com.blockchain.mercury.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERDETAIL")
public class UserDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "userId")
    private int userId;
    @Column(name = "token", length = 20)
    private String token;
    @Column(name = "quantity")
    private double quantity;

    public UserDetail(){}

    public UserDetail(int userId, String token, double quantity) {
        this.userId = userId;
        this.token = token;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
