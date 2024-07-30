package com.turkcell.customer_service.annotations.validation;

import com.turkcell.customer_service.business.constants.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
public @interface BirthDate {
    String message() default Messages.CustomerMessages.BIRTH_DATE_NOT_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
