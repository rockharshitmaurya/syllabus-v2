package com.syllabus.tagv2.service;

import com.syllabus.tagv2.dto.request_dto.TagRelationDto;
import com.syllabus.tagv2.dto.response_dto.SyllabusWithTagsResponseDTO;
import com.syllabus.tagv2.dto.response_dto.TagResponseDTO;
import com.syllabus.tagv2.entity.SyllabusTag;
import com.syllabus.tagv2.entity.Tag;
import com.syllabus.tagv2.feign.SyllabusInterface;
import com.syllabus.tagv2.model.Message;
import com.syllabus.tagv2.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManageTagRelationImpl implements ManageTagRelation{

    @Autowired
    TagRepository tagRepository;

    @Autowired
    SyllabusInterface syllabusInterface;

    @Autowired
    EntityManager entityManager;

    @Override
    public ResponseEntity<?> attachTag(TagRelationDto data) {
        try {
            Integer syllabus_status=syllabusInterface.isSyllabusExists(data.getSyllabusId()).getBody();
            Tag tag = entityManager.find(Tag.class, data.getTagId());

            if (tag != null && syllabus_status==1) {
                return tagRepository.attachTag(data,tag);
            } else {
                return ResponseEntity.status(404).body(new Message("Syllabus or Tag not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Message("Error adding SyllabusTag"));
        }
    }

    @Override
    @Transactional
    public Message detachTag(TagRelationDto data) {
        Query query=entityManager.createQuery("DELETE from SyllabusTag where syllabusId=:s_id AND tagId=:t_id");
        query.setParameter("s_id",data.getSyllabusId());
        query.setParameter(("t_id"),entityManager.find(Tag.class,data.getTagId()));
        int rows=query.executeUpdate();
        if(rows>0) return new Message("Tag Removed from the Syllabus");
        return new Message("No Data Found with the associated Ids");
    }

    @Override
    public ResponseEntity<?> getSyllabusWithTags() {
        String queryString = "SELECT DISTINCT s.*, st.tag_id, t.tag FROM syllabus s LEFT JOIN syllabus_tag st ON s.syllabus_id = st.syllabus_id LEFT JOIN tag t ON st.tag_id = t.tag_id";

        Query query = entityManager.createNativeQuery(queryString);

        List<Object[]> results = query.getResultList();
        List<SyllabusWithTagsResponseDTO> responseList = mapResultsToDTOs(results);

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    private List<SyllabusWithTagsResponseDTO> mapResultsToDTOs(List<Object[]> results) {
        Map<Long, SyllabusWithTagsResponseDTO> syllabusMap = new HashMap<>();

        for (Object[] result : results) {
            Long syllabusId = Long.parseLong(result[0].toString());

            SyllabusWithTagsResponseDTO responseDTO = syllabusMap.getOrDefault(syllabusId, new SyllabusWithTagsResponseDTO());

            if (responseDTO.getSyllabusId() == null) {
                responseDTO.setSyllabusId(syllabusId);
                responseDTO.setName((String) result[1]);
                responseDTO.setStatus((String) result[2]);
                responseDTO.setMedium((String) result[3]);
                responseDTO.setDescription((String) result[4]);
                responseDTO.setTags(new ArrayList<>());
            }

            Long tagId = result[5]!=null ? Long.parseLong(result[5].toString()) : null;
            String tagName = (String) result[6];

            if (tagId != null && tagName != null) {
                TagResponseDTO tagResponseDTO = new TagResponseDTO();
                tagResponseDTO.setTagId(tagId);
                tagResponseDTO.setTag(tagName);
                responseDTO.getTags().add(tagResponseDTO);
            }

            syllabusMap.put(syllabusId, responseDTO);
        }

        return new ArrayList<>(syllabusMap.values());
    }
}
