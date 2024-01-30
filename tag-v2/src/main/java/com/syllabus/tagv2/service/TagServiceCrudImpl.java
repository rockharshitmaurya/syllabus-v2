package com.syllabus.tagv2.service;

import com.syllabus.tagv2.dto.request_dto.TagDto;
import com.syllabus.tagv2.entity.SyllabusTag;
import com.syllabus.tagv2.entity.Tag;
import com.syllabus.tagv2.model.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TagServiceCrudImpl implements TagServiceCrud {
    private final EntityManager entityManager;

    @Autowired
    public TagServiceCrudImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public ResponseEntity<?> addTag(TagDto tagDTO) {
        try {
            // Validate request parameters
            if (tagDTO == null || tagDTO.getTagName() == null || tagDTO.getTagName().isEmpty()) {
                return ResponseEntity.badRequest().body(new Message("Tag name cannot be empty"));
            }

            if(!tagDTO.getTagName().trim().matches("[a-zA-Z ]+")){
                return ResponseEntity.badRequest().body(new Message("Invalid TagName"));
            }


            // Check if the tag already exists in the database
            if (tagExists(tagDTO.getTagName())) {
                return ResponseEntity.badRequest().body(new Message("Tag already exists"));
            }

            Tag tag = new Tag();
            tag.setTag(tagDTO.getTagName());
            entityManager.persist(tag);

            return ResponseEntity.ok(new Message("Tag added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Message("Error adding tag"));
        }
    }
    @Override
    public ResponseEntity<?> getAllTags() {
        List<Tag> tags = entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        return ResponseEntity.ok(tags);
    }

    @Override
    public ResponseEntity<?> getTagById(Long tagId) {
        // Find the Tag entity in the database by its ID
        Tag tag = entityManager.find(Tag.class, tagId);

        if (tag != null) {
            return ResponseEntity.ok(tag);
        } else {
            // Return a not found response if the Tag with the given ID does not exist
            return ResponseEntity.status(204).body(new Message("Tag not found"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateTag(Long tagId, TagDto tagDto) {
        try {
            // Validate request parameters
            if (tagDto == null || tagDto.getTagName() == null || tagDto.getTagName().isEmpty()) {
                return ResponseEntity.badRequest().body(new Message("Invalid TagDto or tag name"));
            }

            // Find the existing Tag in the database by its ID
            Tag existingTag = entityManager.find(Tag.class, tagId);

            if (existingTag != null) {

                existingTag.setTag(tagDto.getTagName());
                entityManager.merge(existingTag);

                return ResponseEntity.ok(new Message("Tag updated successfully"));
            } else {
                return ResponseEntity.status(404).body(new Message("Tag not found"));
            }
        } catch (Exception e) {
            // Return a server error response (HTTP 500 Internal Server Error) if an exception occurs during the operation
            return ResponseEntity.status(500).body(new Message("Error updating tag"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteTag(Long tagId) {
        Tag tag = entityManager.find(Tag.class, tagId);

        if (tag != null) {
            // Check if the Tag is associated with any SyllabusTag entities
            int rows = entityManager.createQuery("SELECT st From SyllabusTag st where tagId=:t_id")
                    .setParameter("t_id", tag)
                    .getResultList().size();

            if (rows > 0) {
                // Return a conflict response (HTTP 409 Conflict) if the Tag cannot be deleted while in use
                return ResponseEntity.status(409).body(new Message("Tag cannot be deleted while in use"));
            } else {
                entityManager.remove(tag);
                return ResponseEntity.ok(new Message("Tag deleted successfully"));
            }
        } else {
            return ResponseEntity.status(404).body(new Message("Tag not found"));
        }
    }

    // Helper method to check if the tag already exists in the database
    private boolean tagExists(String tagName) {
        Query query = entityManager.createQuery("SELECT COUNT(t) FROM Tag t WHERE t.tag = :tagName")
                .setParameter("tagName", tagName);

        Long count = (Long) query.getSingleResult();

        // If count is greater than 0, the tag already exists
        return count > 0;
    }

}
