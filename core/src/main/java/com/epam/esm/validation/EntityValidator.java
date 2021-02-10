package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.NewOrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class EntityValidator {

    private final BasicValidator basicValidator;
    private Set<String> certificateFieldNames;

    {
        certificateFieldNames = new HashSet<>();
        certificateFieldNames.add(Constant.NAME_FIELD);
        certificateFieldNames.add(Constant.DESCRIPTION_FIELD);
        certificateFieldNames.add(Constant.PRICE_FIELD);
        certificateFieldNames.add(Constant.DURATION_FIELD);
        certificateFieldNames.add(Constant.TAGS_FIELD);
    }

    public void validateTag(TagDto tagDto) {
        basicValidator.validateNonNull(tagDto, Tag.class.getSimpleName());
        basicValidator.validateStringField(tagDto.getName(), "tag name");
    }

    public void validateCertificate(CertificateDto certificateDto) {
        basicValidator.validateNonNull(certificateDto, CertificateDto.class.getName());
        basicValidator.validateStringField(certificateDto.getName(), "certificate name");
        basicValidator.validateStringField(certificateDto.getDescription(), "description");
        validatePrice(certificateDto.getPrice());
        validateDuration(certificateDto.getDuration());
    }


    public void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new ValidationException(ExceptionCode.CANNOT_BE_NULL.getErrorCode(), "price");
        }
        if (price.doubleValue() <= 0) {
            throw new ValidationException(ExceptionCode.SHOULD_BE_POSITIVE.getErrorCode(), "price = " + price);
        }
        if (price.doubleValue() > 999999999.99) {
            throw new ValidationException(ExceptionCode.PRICE_TOO_HIGH.getErrorCode(), "price = " + price);
        }
    }

    public void validateDuration(int duration) {
        if (duration <= 0) {
            throw new ValidationException(
                    ExceptionCode.SHOULD_BE_POSITIVE.getErrorCode(), "duration = " + duration);
        }
        if (duration > 366) {
            throw new ValidationException(
                    ExceptionCode.DURATION_CANNOT_BE_MORE_THAN_YEAR.getErrorCode(), "duration = " + duration);
        }
    }

    public void validateOrder(NewOrderDto newOrderDto) {
        basicValidator.validateNonNull(newOrderDto.getUserId(), "userId");
        basicValidator.validateNonNull(newOrderDto.getCertificatesId(), "certificatesId");
        basicValidator.validateIdIsPositive(newOrderDto.getUserId());
        if (newOrderDto.getCertificatesId().isEmpty()) {
            throw new ValidationException(ExceptionCode.CANNOT_BE_EMPTY.getErrorCode(), "certificatesId");
        }
    }


    private static class Constant {
        private static final String NAME_FIELD = "name";
        private static final String DESCRIPTION_FIELD = "description";
        private static final String TAGS_FIELD = "tags";
        private static final String DURATION_FIELD = "duration";
        private static final String PRICE_FIELD = "price";
    }
}
