package at.technikum.bweng.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Locale;

public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.isBlank() || Arrays.asList(Locale.getISOCountries()).contains(value);
    }

}
