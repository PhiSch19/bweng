package at.technikum.bweng.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TimeConflictValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeConflictConstraint {
    String message() default "Time is already booked";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
