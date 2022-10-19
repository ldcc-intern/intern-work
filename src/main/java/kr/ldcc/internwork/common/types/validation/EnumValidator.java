package kr.ldcc.internwork.common.types.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, java.lang.Enum> {
    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(java.lang.Enum value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.toString().equals(enumValue.toString())) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}