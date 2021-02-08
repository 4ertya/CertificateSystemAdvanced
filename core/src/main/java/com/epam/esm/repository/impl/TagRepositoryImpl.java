package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Tag> findAllTags(int limit, int offset) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        criteriaQuery.from(Tag.class);
        return entityManager.createQuery(criteriaQuery).setMaxResults(limit).setFirstResult(offset).getResultList();
    }

    @Override
    public Tag findTagByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));
        return entityManager.createQuery(criteriaQuery).getResultStream().findFirst().orElse(null);
    }

    @Override
    public Tag findTagById(long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag createTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void updateTag(Tag tag) {
        entityManager.merge(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        entityManager.remove(tag);
    }

    @Override
    public long getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> count = criteriaBuilder.createQuery(Long.class);
        count.select(criteriaBuilder.count(count.from(Tag.class)));
        return entityManager.createQuery(count).getSingleResult();
    }
}
