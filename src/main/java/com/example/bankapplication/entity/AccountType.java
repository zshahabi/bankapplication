package com.example.bankapplication.entity;

public enum AccountType {
    CURRENT("current"),LONGTERM("longterm"),
    SHORTTERM("shortterm")
    ,GOODLOAN("goodloan")
    ;
    private String name;

    private AccountType (String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
