package com.epam.esm.util;

public enum  ControllerConstant {
    PAGES_NUMBER("pages number"),
    CURRENT_PAGE( "current page"),
    ELEMENTS_PER_PAGE("elements per page"),
    ELEMENTS_NUMBER ("total number of elements");

    private String value;

    ControllerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
