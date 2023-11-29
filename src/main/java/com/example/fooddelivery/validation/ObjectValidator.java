package com.example.fooddelivery.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ObjectValidator {

    private final ValidatorFactory vf = Validation.buildDefaultValidatorFactory();

    private final Validator validator = vf.getValidator();

    public Map<String, String> validate(Object object) {

        Set<ConstraintViolation<Object>> validationProblems = validator.validate(object);

        Map<String, String> violatioMap = new HashMap<>();

        for(ConstraintViolation<Object> violation : validationProblems){
            violatioMap.put(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()
            );
        }
        return violatioMap;
    }
}