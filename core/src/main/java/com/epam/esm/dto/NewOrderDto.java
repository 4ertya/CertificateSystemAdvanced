package com.epam.esm.dto;

import java.util.List;

public class NewOrderDto {
    private Long userId;
    private List<Long> certificatesId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getCertificatesId() {
        return certificatesId;
    }

    public void setCertificatesId(List<Long> certificatesId) {
        this.certificatesId = certificatesId;
    }
}
