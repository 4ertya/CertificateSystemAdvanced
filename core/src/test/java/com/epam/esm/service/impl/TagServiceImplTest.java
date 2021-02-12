package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.validation.BasicValidator;
import com.epam.esm.validation.EntityValidator;
import com.epam.esm.validation.PaginationValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Spy
    private PaginationValidator paginationValidator;
    @Spy
    BasicValidator basicValidator;
    @Mock
    EntityValidator entityValidator;
    @Spy
    private TagMapper mapper = new TagMapper(new ModelMapper());

    @Test
    void findAllTags() {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("tag2");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag1);
        tags.add(tag2);

        TagDto tagDTO1 = new TagDto();
        tagDTO1.setId(1L);
        tagDTO1.setName("tag1");
        TagDto tagDTO2 = new TagDto();
        tagDTO2.setId(2L);
        tagDTO2.setName("tag2");
        List<TagDto> tagDTOs = new ArrayList<>();
        tagDTOs.add(tagDTO1);
        tagDTOs.add(tagDTO2);
        when(tagRepository.findAllTags(10, 0)).thenReturn(tags);
        Assertions.assertEquals(tagDTOs, tagService.findAllTags(new HashMap<>()));
    }

    @Test
    void findTagById() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("entertainment");
        when(tagRepository.findTagById(1)).thenReturn(tag);
        TagDto returnedTag = tagService.findTagById(1);
        assertEquals(tag.getName(), returnedTag.getName());
    }


    @Test
    void createTag() {
        String name = "newTag";
        Tag tag = new Tag();
        tag.setName(name);
        Tag tagReturned = new Tag();
        tagReturned.setName(tag.getName());
        tagReturned.setId(24L);
        when(tagRepository.createTag(tag)).thenReturn(tagReturned);
        TagDto tagDto = new TagDto();
        tagDto.setName(tag.getName());
        TagDto tagDTOReturned = tagService.createTag(tagDto);
        assertEquals(tagDTOReturned.getId(), tagReturned.getId());
    }

    @Test
    void deleteTag() {
        doNothing().when(tagRepository).deleteTag(new Tag());
        when(tagRepository.findTagById(3)).thenReturn(new Tag());
        tagService.deleteTag(3);
    }

}