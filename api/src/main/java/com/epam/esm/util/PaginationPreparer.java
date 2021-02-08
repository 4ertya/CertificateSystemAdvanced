package com.epam.esm.util;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PaginationPreparer {

    public List<Link> preparePaginationLinks(Object invocationValue, Map<String, String> params, long entityCount) {
        int currentPage = getPageNumber(params);
        long pageCount = calculatePageCount(params, entityCount);
        List<Link> links = new ArrayList<>();
        if (currentPage != pageCount) {
            params.replace("page", String.valueOf(currentPage + 1));
            links.add(linkTo(invocationValue).withRel("next page"));
        }
        if (currentPage != 1) {
            params.replace("page", String.valueOf(currentPage - 1));
            links.add(linkTo(invocationValue).withRel("previous page"));
        }
        return links;
    }


    public Map<String, Long> preparePageInfo(Map<String, String> params, long entityCount) {
        int currentPage = getPageNumber(params);
        long pageCount = calculatePageCount(params, entityCount);
        Map<String, Long> page = new HashMap<>();
        page.put(ControllerConstant.PAGES_NUMBER.getValue(), pageCount);
        page.put(ControllerConstant.CURRENT_PAGE.getValue(), (long) currentPage);
        page.put(ControllerConstant.ELEMENTS_PER_PAGE.getValue(), Long.parseLong(params.get("size")));
        page.put(ControllerConstant.ELEMENTS_NUMBER.getValue(), entityCount);
        return page;
    }

    private int getPageNumber(Map<String, String> params) {
        return Integer.parseInt(params.get("page"));
    }

    private long calculatePageCount(Map<String, String> params, long entityCount) {
        return (long) Math.ceil(entityCount / Double.parseDouble(params.get("size")));
    }
}
