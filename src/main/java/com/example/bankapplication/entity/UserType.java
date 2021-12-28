package com.example.bankapplication.entity;

public enum UserType {
    GOODDEALLER("gooddealler"),BADDEALLER("baddealler"),NOHISTORY("nohistory");
    private String name;

    private UserType (String name) {
     this.name=name;
    }

    public String getName() {
        return name;
    }
}
