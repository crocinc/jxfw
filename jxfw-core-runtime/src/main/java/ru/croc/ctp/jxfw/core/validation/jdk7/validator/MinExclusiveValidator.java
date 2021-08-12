package ru.croc.ctp.jxfw.core.validation.jdk7.validator;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.temporal.Temporal;
import ru.croc.ctp.jxfw.core.validation.meta.XFWMinExclusive;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидация для минимальной даты.
 *
 * @author Nosov Alexander
 * @since 1.1
 */
public class MinExclusiveValidator implements ConstraintValidator<XFWMinExclusive, Temporal> {

    private String constraintDateStr;

    @Override
    public void initialize(XFWMinExclusive constraintAnnotation) {
        constraintDateStr = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Temporal date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }
        if (date instanceof LocalDate) {
            final LocalDate minDate = LocalDate.parse(constraintDateStr);
            return ((LocalDate) date).isAfter(minDate);
        }
        if (date instanceof LocalDateTime) {
            final LocalDateTime minDate = LocalDateTime.parse(constraintDateStr);
            return ((LocalDateTime) date).isAfter(minDate);
        }
        if (date instanceof LocalTime) {
            final LocalTime minDate = LocalTime.parse(constraintDateStr);
            return ((LocalTime) date).isAfter(minDate);
        }
        throw new RuntimeException("Unknown date/time format");

    }
}
