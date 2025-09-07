package com.example.chat_service.validator.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValueValidator implements ConstraintValidator<ValidEnum, String> {

    private Set<String> accepted;

    @Override
    public void initialize(ValidEnum annotation) {
        this.accepted = Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(e -> ((Enum<?>) e).name()) // 그대로 보관
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null || value.isBlank()) return true;
        boolean ok = accepted.contains(value); // 대소문자 그대로 비교

        if (!ok) {
            String list = String.join(", ", accepted);
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(
                    "must be one of [" + list + "]"
            ).addConstraintViolation();
        }
        return ok;
    }
}