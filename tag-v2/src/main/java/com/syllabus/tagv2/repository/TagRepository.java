package com.syllabus.tagv2.repository;

import com.syllabus.tagv2.dto.request_dto.TagRelationDto;
import com.syllabus.tagv2.entity.SyllabusTag;
import com.syllabus.tagv2.entity.Tag;
import com.syllabus.tagv2.model.Message;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TagRepository {
    EntityManager entityManager;

    public TagRepository(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Transactional
    public ResponseEntity<?> attachTag(TagRelationDto data,Tag tag){
                SyllabusTag syllabusTag = new SyllabusTag();
                syllabusTag.setSyllabusId(data.getSyllabusId());
                syllabusTag.setTagId(tag);
                entityManager.persist(syllabusTag);
                return ResponseEntity.ok(new Message("Tag Attached successfully"));
    }

}
