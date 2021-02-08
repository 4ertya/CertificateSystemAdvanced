package com.epam.esm.controller;


import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
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
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;
    private final HateaosBuilder hateaosBuilder;

    @GetMapping
    public RepresentationModel<?> findAllTags(@RequestParam Map<String, String> params) {
        List<TagDto> tags = tagService.findAllTags(params);
        long tagsCount = tagService.getCount();
        return hateaosBuilder.addLinksForListOfTagDTOs(tags, params, tagsCount);
    }

    @GetMapping("/{id}")
    public TagDto findTagById(@PathVariable("id") long id) {
        TagDto tagDto = tagService.findTagById(id);
        return hateaosBuilder.addLinksForTagDTO(tagDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagDto createTag(@RequestBody TagDto tagDto) {
        TagDto tag = tagService.createTag(tagDto);
        return hateaosBuilder.addLinksForTagDTO(tag);
    }

    @PutMapping("/{id}")
    public TagDto updateTag(@PathVariable("id") long id, @RequestBody TagDto tagDto) {
        tagDto.setId(id);
        TagDto tag = tagService.updateTag(tagDto);
        return hateaosBuilder.addLinksForTagDTO(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTag(@PathVariable("id") long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
