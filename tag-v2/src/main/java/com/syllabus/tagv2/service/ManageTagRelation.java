package com.syllabus.tagv2.service;

import com.syllabus.tagv2.dto.request_dto.TagRelationDto;
import com.syllabus.tagv2.model.Message;
import org.springframework.http.ResponseEntity;

public interface ManageTagRelation {
    ResponseEntity<?> attachTag(TagRelationDto data);

    Message detachTag(TagRelationDto data);
    ResponseEntity<?> getSyllabusWithTags();
}
