package com.syllabus.api.controller;

import com.syllabus.api.constants.ConstantMessage;
import com.syllabus.api.dto.request_dto.SyllabusDto;
import com.syllabus.api.entity.Syllabus;
import com.syllabus.api.model.Message;
import com.syllabus.api.service.SyllabusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/syllabus-v2")
@EnableCaching
@CrossOrigin("*")
public class SyllabusController {

    @Autowired
    SyllabusService syllabusService;

    @GetMapping
    public ResponseEntity<Message> welcomeMessage() {
        String message = "Welcome to my syllabus-v2 MSA";
        return ResponseEntity.ok(new Message(ConstantMessage.WELCOME_MESSAGE));
    }

    @GetMapping("/getAllSyllabus")
    public ResponseEntity<?> getAllSyllabus() {
        List<Syllabus> syllabusList=syllabusService.getAllSyllabus();
        if(syllabusList.isEmpty()) return ResponseEntity.status(204).body(new Message(ConstantMessage.NO_RECORD_FOUND));
        return ResponseEntity.ok(syllabusList);
    }

    @GetMapping("/syllabus/getSyllabusById/{id}")
    public ResponseEntity<?> getSyllabusById(@PathVariable String id) {
        return syllabusService.getSyllabusById(Long.parseLong(id));
    }

    @GetMapping("/syllabus/exists/{id}")
    public ResponseEntity<?> isSyllabusExists(@PathVariable String id) {
        return syllabusService.idExistOrNot(Long.parseLong(id));
    }

    @PostMapping("/createSyllabus")
    public ResponseEntity<?> createSyllabus(@Valid @RequestBody SyllabusDto syllabus) {
        return syllabusService.createSyllabus(syllabus);
    }

    @PutMapping("/updateSyllabus/{id}")
    public ResponseEntity<?> updateSyllabus(@PathVariable String id, @RequestBody Syllabus syllabus) {
        return syllabusService.updateSyllabus(Long.parseLong(id),syllabus);
    }

    @DeleteMapping("/deleteSyllabus/{id}")
    public ResponseEntity<?> deleteSyllabus(@PathVariable String id) {
        return syllabusService.deleteSyllabus(Long.parseLong(id));
    }

}
