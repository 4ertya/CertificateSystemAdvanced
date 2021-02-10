package com.epam.esm.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private final LocalDateTime localDateTime;
    private final String code;
    private final String error;
    private final String message;
    private String path;


    public ExceptionResponse(String code, String error, String message, String path) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.path=path;
        localDateTime=LocalDateTime.now(ZoneOffset.UTC);
    }

    public ExceptionResponse(String code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
        localDateTime=LocalDateTime.now(ZoneOffset.UTC);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
