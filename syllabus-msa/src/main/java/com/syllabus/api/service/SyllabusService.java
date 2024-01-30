package com.syllabus.api.service;


import com.syllabus.api.dto.request_dto.SyllabusDto;
import com.syllabus.api.entity.Syllabus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SyllabusService {
    List<Syllabus> getAllSyllabus();

    ResponseEntity<?> createSyllabus(SyllabusDto syllabusDto);

    ResponseEntity<?> getSyllabusById(Long id);

    ResponseEntity<?> updateSyllabus(Long id, Syllabus syllabus);

    ResponseEntity<?> deleteSyllabus(Long id);


    ResponseEntity<Integer> idExistOrNot(Long id);
}
