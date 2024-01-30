package com.syllabus.tagv2.service;

import com.syllabus.tagv2.dto.request_dto.TagDto;
import org.springframework.http.ResponseEntity;

public interface TagServiceCrud {
    ResponseEntity<?> addTag(TagDto tagDto);

    ResponseEntity<?> getAllTags();

    ResponseEntity<?> getTagById(Long tagId);

    ResponseEntity<?> updateTag(Long tagId, TagDto tagDto);

    ResponseEntity<?> deleteTag(Long tagId);
}
