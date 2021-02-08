package com.epam.esm.validation;

import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.exception.ValidationException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaginationValidator {

    public void validatePaginationParams(Map<String, String> params) {
        validateParameters(params);
    }

    public void validatePageNumber(Map<String, String> params, long elementsCount) {
        int page = Integer.parseInt(params.get(Constant.PAGE_PARAM));
        int size = Integer.parseInt(params.get(Constant.SIZE_PARAM));
        int totalPagesAmount = (int) Math.ceil(elementsCount / (double) size);
        if (page > totalPagesAmount) {
            throw new ValidationException(
                    ExceptionCode.PAGE_IS_GREATER_THAN_TOTAL_AMOUNT_OF_PAGES.getErrorCode(),
                    Constant.PAGE_PARAM + Constant.EQUAL_SIGN + page);
        }
    }


    private void validateParameters(Map<String, String> params) {
        if (!params.containsKey("size")) {
            params.put("size", String.valueOf(10));
        } else {
            validateParameterValue(params.get("size"), "size");
        }
        if (!params.containsKey("page")) {
            params.put("page", String.valueOf(1));
        } else {
            validateParameterValue(params.get("page"), "page");
        }
    }

    private void validateParameterValue(String value, String paramName) {
        if (NumberUtils.isParsable(value) && NumberUtils.createNumber(value).getClass().equals(Integer.class)) {
            int pageNumber = Integer.parseInt(value);
            if (pageNumber < 1) {
                throw new ValidationException(ExceptionCode.SHOULD_BE_POSITIVE.getErrorCode(),
                        paramName + Constant.EQUAL_SIGN + value);
            }
        } else {
            throw new ValidationException(ExceptionCode.DATA_TYPE_DOES_NOT_MATCH_REQUIRED.getErrorCode(), paramName);
        }
    }

    private static class Constant {
        private final static String SIZE_PARAM = "size";
        private final static String PAGE_PARAM = "page";
        private final static String EQUAL_SIGN = " = ";
    }
}
