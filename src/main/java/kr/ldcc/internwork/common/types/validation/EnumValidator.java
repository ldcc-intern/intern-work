package kr.ldcc.internwork.common.types.validation;

import kr.ldcc.internwork.common.exception.ExceptionCode;
import kr.ldcc.internwork.common.exception.InternWorkException;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
public class EnumValidator implements ConstraintValidator<Enum, Object> {
    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            log.error("@Enum Exception | " + annotation.enumClass().getSimpleName() + " : null", ExceptionCode.ENUM_NULL_POINTER_EXCEPTION);
            throw new InternWorkException.enumNullPointerException();
        }
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
