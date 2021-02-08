package com.epam.esm.controller;


import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.HateaosBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;
    private final HateaosBuilder hateaosBuilder;

    @GetMapping
    public RepresentationModel<?> findAllCertificates(@RequestParam Map<String, String> params) {
        List<CertificateDto> certificates = certificateService.findCertificates(params);
        long certificatesCount = certificateService.getCount(params);
        return hateaosBuilder.addLinksForListOfCertificates(certificates, params, certificatesCount);
    }

    @GetMapping("/{id}")
    public RepresentationModel<?> findCertificateById(@PathVariable long id) {
        CertificateDto certificate = certificateService.findCertificateById(id);
        return hateaosBuilder.addLinksForCertificate(certificate);
    }

    @PostMapping
    public CertificateDto createCertificate(@RequestBody CertificateDto certificateDto) {
        CertificateDto certificate = certificateService.createCertificate(certificateDto);
        return hateaosBuilder.addLinksForCertificate(certificate);
    }

    @PatchMapping("/{id}")
    public CertificateDto updateCertificate(@PathVariable long id, @RequestBody CertificateDto certificateDto) {
        certificateDto.setId(id);
        CertificateDto certificate = certificateService.updateCertificate(certificateDto);
        return hateaosBuilder.addLinksForCertificate(certificate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCertificate(@PathVariable long id) {
        certificateService.deleteCertificate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
