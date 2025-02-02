package com.example.money_meow.account;


import com.example.money_meow.database.insert.AddUserToDB;
import com.example.money_meow.database.insert.MongoDBInsert;

import org.bson.Document;

public class  Account implements AddUserToDB{
    private String name;
    private String userName;
    private String email;
    private String password;
    private Double balance;

    public Account() {

    }

    public Account(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.balance = new Double(0);
    }

    public Account(String name, String userName, String email, String password, double amount) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.balance = amount;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public void addNewUserToDB() {

        MongoDBInsert.insertOne("MoneyMeow",
                        "users",
                        new Document()
                        .append("name", this.name)
                        .append("userName", this.userName)
                        .append("email", this.email)
                        .append("password", this.password)
                        .append("balance", this.balance));
    }
}
