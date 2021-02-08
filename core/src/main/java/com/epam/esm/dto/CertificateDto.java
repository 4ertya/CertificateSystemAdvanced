package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class CertificateDto extends RepresentationModel<CertificateDto> {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;
}
