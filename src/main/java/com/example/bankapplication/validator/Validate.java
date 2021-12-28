package com.example.bankapplication.validator;

public interface Validate<T> {
    boolean validate(T t);
    T realObject();
}
