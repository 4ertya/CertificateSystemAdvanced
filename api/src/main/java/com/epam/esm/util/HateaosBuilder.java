package com.epam.esm.util;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.hal.HalModelBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class HateaosBuilder {
    private final PaginationPreparer paginationPreparer;

    public RepresentationModel<?> addLinksForListOfTagDTOs(List<TagDto> tags, Map<String, String> params, long tagsCount) {
        tags.forEach(tagDTO -> tagDTO.add(linkTo(methodOn(TagController.class)
                .findTagById(tagDTO.getId()))
                .withSelfRel()));

        Map<String, Long> page = paginationPreparer.preparePageInfo(params, tagsCount);
        List<Link> links = paginationPreparer.preparePaginationLinks(
                methodOn(TagController.class).findAllTags(params), params, tagsCount);
        CollectionModel<TagDto> collectionModel = CollectionModel.of(tags);
        return buildModel(collectionModel, links, page);
    }

    public TagDto addLinksForTagDTO(TagDto tagDTO) {
        tagDTO.add(linkTo(methodOn(TagController.class)
                .findTagById(tagDTO.getId()))
                .withSelfRel());
        tagDTO.add(createLinkToGetCertificates("tag",
                tagDTO.getName(), "certificates"));
        return tagDTO;
    }




    public RepresentationModel<?> addLinksForListOfCertificates(List<CertificateDto> certificates, Map<String, String> params, long certificatesCount) {
        certificates.forEach(certificate -> certificate.add(linkTo(methodOn(CertificateController.class)
                .findCertificateById(certificate.getId()))
                .withSelfRel()));
        Map<String, Long> page = paginationPreparer.preparePageInfo(params, certificatesCount);
        List<Link> links = paginationPreparer.preparePaginationLinks(
                methodOn(CertificateController.class).findAllCertificates(params),
                params, certificatesCount);
        links.add(createLinkToGetCertificates("orderBy",
                "name", "sort by name asc"));
        links.add(createLinkToGetCertificates("orderBy",
                "-name", "sort by name desc"));
        links.add(createLinkToGetCertificates("orderBy",
                "date", "sort by create date asc"));
        links.add(createLinkToGetCertificates("orderBy",
                "-date", "sort by create date desc"));
        CollectionModel<CertificateDto> collectionModel = CollectionModel.of(certificates);
        return buildModel(collectionModel, links, page);
    }

    public CertificateDto addLinksForCertificate(CertificateDto certificateDto) {
        certificateDto.getTags().forEach(tag -> tag.add(linkTo(methodOn(TagController.class)
                .findTagById(tag.getId()))
                .withSelfRel()));
        return certificateDto;
    }

    private Link createLinkToGetCertificates(String param, String value, String rel) {
        Map<String, String> params = new HashMap<>();
        params.put(param, value);
        return linkTo(methodOn(CertificateController.class)
                .findAllCertificates(params))
                .withRel(rel);
    }

    private RepresentationModel<?> buildModel(Object entity, Iterable<Link> links, Object embeddedEntity) {
        return HalModelBuilder
                .halModelOf(entity)
                .links(links)
                .embed(embeddedEntity, LinkRelation.of("page"))
                .build();
    }
}
