package com.example.chat_service.validator.common;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EnumValueValidator.class)
public @interface ValidEnum {
    String message() default "값이 유효하지 않습니다. 유효한 값: {acceptedValues}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass(); // 대상 enum
    boolean ignoreCase() default false;   // 이제 기본은 대소문자 구분
}