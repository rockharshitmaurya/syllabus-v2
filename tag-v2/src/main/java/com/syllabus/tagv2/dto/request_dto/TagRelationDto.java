package com.syllabus.tagv2.dto.request_dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRelationDto {
    Long syllabusId;
    Long tagId;
}
