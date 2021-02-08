package com.epam.esm.exception;

public class EntityNotFoundException extends RuntimeException {
    private final Long id;

    public EntityNotFoundException(String errorCode, long id) {
        super(errorCode);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
