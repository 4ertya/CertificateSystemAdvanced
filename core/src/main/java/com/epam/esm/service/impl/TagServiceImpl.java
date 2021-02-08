package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ExceptionCode;
import com.epam.esm.exception.ValidationException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validation.BasicValidator;
import com.epam.esm.validation.EntityValidator;
import com.epam.esm.validation.PaginationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final PaginationValidator paginationValidator;
    private final EntityValidator entityValidator;
    private final BasicValidator basicValidator;

    @Override
    public List<TagDto> findAllTags(Map<String, String> params) {
        paginationValidator.validatePaginationParams(params);
        int limit = Integer.parseInt(params.get("size"));
        int offset = (Integer.parseInt(params.get("page")) - 1) * limit;
        return tagRepository.findAllTags(limit, offset).stream().map(tagMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public TagDto findTagById(long id) {
        basicValidator.validateIdIsPositive(id);
        Tag tag = tagRepository.findTagById(id);
        if (tag == null) {
            throw new EntityNotFoundException(ExceptionCode.NON_EXISTING_TAG.getErrorCode(), id);
        }
        return tagMapper.toDTO(tag);
    }

    @Override
    public TagDto findTagByName(String name) {
        Tag tag = tagRepository.findTagByName(name);
        return tagMapper.toDTO(tag);
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        entityValidator.validateTag(tagDto);
        tagDto.setId(null);
        checkExistence(tagDto);
        Tag tag = tagRepository.createTag(tagMapper.toModel(tagDto));
        return tagMapper.toDTO(tag);
    }

    @Override
    public void deleteTag(long id) {
        basicValidator.validateIdIsPositive(id);
        Tag tag = tagRepository.findTagById(id);
        if (tag == null) {
            throw new EntityNotFoundException(ExceptionCode.NON_EXISTING_TAG.getErrorCode(), id);
        }
        tagRepository.deleteTag(tag);
    }

    @Override
    public TagDto updateTag(TagDto tagDto) {
        basicValidator.validateIdIsPositive(tagDto.getId());
        checkExistence(tagDto);
        Tag tag = tagMapper.toModel(tagDto);
        tagRepository.updateTag(tag);
        return tagDto;
    }

    private void checkExistence(TagDto tag) {
        Tag tagReturned = tagRepository.findTagByName(tag.getName());
        if (tagReturned != null) {
            throw new ValidationException(
                    ExceptionCode.TAG_ALREADY_EXIST.getErrorCode(),
                    tag.getName() + ", id = " + tagReturned.getId());
        }
    }

    @Override
    public long getCount() {
        return tagRepository.getCount();
    }
}