package com.example.bankapplication.validator;




import javax.validation.*;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;


import java.util.Set;

public class ValidatorImpl<T> implements Validate<T> {
    private T t;
    @Override
    public boolean validate(T t) {
this.t=t;

try {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    ;
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<T>> cvs = validator.validate(t);

    if (!cvs.isEmpty()) {
        for (ConstraintViolation constraintViolation : cvs) {
            System.out.println(constraintViolation.getMessage());
        }
        return false;
    }
    return true;
} catch (Exception ex){
    System.out.println(ex.getMessage());
}
return false;
    }

    @Override
    public T realObject() {
        return t;
    }

}
