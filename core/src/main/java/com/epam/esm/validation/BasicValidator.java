package com.epam.esm.validation;

import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class BasicValidator {

    private final Set<String> orderByValues;

    {
        orderByValues = new HashSet<>();
        orderByValues.add(Constants.SORT_BY_DATE_ASC);
        orderByValues.add(Constants.SORT_BY_DATE_DESC);
        orderByValues.add(Constants.SORT_BY_NAME_ASC);
        orderByValues.add(Constants.SORT_BY_NAME_DESC);
    }

    public void checkTagNameParams(Map<String, String> params) {
        if (params.containsKey(Constants.TAG_PARAM)) {
            params.replace(Constants.TAG_PARAM,
                    validateTagNames(params.get(Constants.TAG_PARAM)));
        }
    }

    private String validateTagNames(String tagNamesAsString) {
        List<String> validatedTags = new ArrayList<>();
        String[] tagNames = tagNamesAsString.split(",");
        for (String tagName : tagNames) {
            validateStringField(tagName, "tag name");
            validatedTags.add(tagName.trim().toLowerCase());
        }
        return String.join(Constants.COMMA, validatedTags.toArray(new String[0]));
    }

    public void checkOrderByParams(Map<String, String> params) {
        if (params.containsKey(Constants.ORDER_BY)) {
            validateOrderByValue(params.get(Constants.ORDER_BY));
        }
    }

    private void validateOrderByValue(String value) {
        if (!orderByValues.contains(value)) {
            throw new ValidationException(ExceptionCode.INVALID_ORDER_BY_VALUE.getErrorCode(), value);
        }
    }

    public void validateIdIsPositive(long id) {
        if (id <= 0) {
            throw new ValidationException(ExceptionCode.SHOULD_BE_POSITIVE.getErrorCode(),
                    Constants.ID + Constants.EQUAL_SIGN + id);
        }
    }

    public void validateNonNull(Object object, String className) {
        if (object == null) {
            throw new ValidationException(ExceptionCode.CANNOT_BE_NULL.getErrorCode(), className);
        }
    }

    public void validateStringField(String string, String field) {
        if (!StringUtils.hasText(string)) {
            throw new ValidationException(ExceptionCode.CANNOT_BE_EMPTY.getErrorCode(), field);
        }
    }

    private static class Constants {
        private static final String SORT_BY_NAME_ASC = "name";
        private static final String SORT_BY_NAME_DESC = "-name";
        private static final String SORT_BY_DATE_ASC = "date";
        private static final String SORT_BY_DATE_DESC = "-date";
        private static final String ORDER_BY = "orderBy";
        private static final String ID = "id";
        private static final String EQUAL_SIGN = " = ";
        private static final String COMMA = ",";
        private static final String TAG_PARAM = "tag";
    }
}
