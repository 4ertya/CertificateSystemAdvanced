package com.epam.esm.repository;


import com.epam.esm.model.Certificate;
import com.epam.esm.repository.specification.Specification;

import java.util.List;

public interface CertificateRepository {

    List<Certificate> findAllCertificates(List<Specification> specifications, int limit, int offset);

//    List<Certificate> findAllCertificatesBySpecification(Specification specification);

    Certificate findCertificateById(long id);

    Certificate createCertificate(Certificate certificate);

    void updateCertificate(Certificate certificate);

    void deleteCertificate(Certificate certificate);

    long getCount(List<Specification> specifications);
}
