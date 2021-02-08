package com.epam.esm.repository.specification;


import com.epam.esm.repository.specification.impl.certificate.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SpecificationCreator {

    private List<Specification> specifications;


    public List<Specification> generateQueryCriteria(Map<String, String> params) {
        specifications = new ArrayList<>();
        params.keySet().forEach(key -> {
            switch (key) {
                case CreatorParams.CERTIFICATE_NAME_PARAM:
                    specifications.add(new GetCertificatesByName(params.get(key)));
                    break;
                case CreatorParams.CERTIFICATE_DESCRIPTION_PARAM:
                    specifications.add(new GetCertificatesByDescription(params.get(key)));
                    break;
                case CreatorParams.TAG_PARAM:
                    specifications.add(new GetCertificatesByTagName(params.get(key)));
                    break;
                case CreatorParams.ORDER_BY_PARAM:
                    addOrderBy(params);
                default:
                    break;
            }
        });
        return specifications;
    }

    private void addOrderBy(Map<String, String> params) {
        String value = params.get(CreatorParams.ORDER_BY_PARAM);

        if (value.startsWith("-")) {
            specifications.add(new SortCertificatesDescending(value));
        } else {
            specifications.add(new SortCertificatesAscending(value));
        }
    }


    private static class CreatorParams {
        private static final String ORDER_BY_PARAM = "orderBy";
        private static final String TAG_PARAM = "tag";
        private static final String CERTIFICATE_NAME_PARAM = "name";
        private static final String CERTIFICATE_DESCRIPTION_PARAM = "description";
    }
}
