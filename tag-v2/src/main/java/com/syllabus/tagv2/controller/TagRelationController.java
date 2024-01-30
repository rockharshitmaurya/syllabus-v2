package com.syllabus.tagv2.controller;

import com.syllabus.tagv2.dto.request_dto.TagRelationDto;
import com.syllabus.tagv2.model.Message;
import com.syllabus.tagv2.service.ManageTagRelation;
import com.syllabus.tagv2.service.TagServiceCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/syllabus-v2/tag/manage")
@CrossOrigin("*")
public class TagRelationController {

    @Autowired
    ManageTagRelation manageTagRelation;

    @PostMapping("/attach-tag")
    ResponseEntity<?> attachTag(@RequestBody TagRelationDto data){
    return manageTagRelation.attachTag(data);
    }

    @PostMapping("/detach-tag")
    ResponseEntity<Message> detachTag(@RequestBody TagRelationDto data){
        System.out.println(data);
        return ResponseEntity.ok(manageTagRelation.detachTag(data));
    }

    @GetMapping("/getSyllabusWithTags")
    ResponseEntity<?> getSyllabusWithTags(){
        return manageTagRelation.getSyllabusWithTags();
    }

}
