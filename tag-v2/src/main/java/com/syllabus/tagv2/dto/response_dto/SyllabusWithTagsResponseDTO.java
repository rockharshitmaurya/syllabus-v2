package com.syllabus.tagv2.dto.response_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusWithTagsResponseDTO{

    private Long syllabusId;
    private String name;
    private String status;
    private String medium;
    private String description;
    private List<TagResponseDTO> tags;
}