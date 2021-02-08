package com.epam.esm.repository.specification.impl.order;


import com.epam.esm.repository.specification.SearchSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class GetOrdersByUserId implements SearchSpecification {

    private final int userId;

    public GetOrdersByUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public <T> Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.equal(root.get("userId"), userId);
    }
}
