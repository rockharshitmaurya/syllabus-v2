package com.syllabus.tagv2.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "SYLLABUS-MSA")
public interface SyllabusInterface {
    @GetMapping("/syllabus-v2/getSyllabusById/{id}")
    public ResponseEntity<?> getSyllabusById(@PathVariable Long id);

    @GetMapping("/syllabus-v2/syllabus/exists/{id}")
    public ResponseEntity<Integer> isSyllabusExists(@PathVariable Long id);

    @GetMapping("/syllabus-v2/getAllSyllabus")
    public ResponseEntity<?> getAllSyllabus();

}
