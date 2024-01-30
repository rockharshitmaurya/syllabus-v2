package com.syllabus.tagv2.controller;


import com.syllabus.tagv2.dto.request_dto.TagDto;
import com.syllabus.tagv2.model.Message;
import com.syllabus.tagv2.service.TagServiceCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/syllabus-v2/tag")
@CrossOrigin("*")
public class TagController {

    private final TagServiceCrud tagService;

    @Autowired
    public TagController(TagServiceCrud tagService) {
        this.tagService = tagService;
    }


    @GetMapping
    ResponseEntity<?> home(){
        return ResponseEntity.ok(new Message("Welcome to tag MSA"));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<?> getTagById(@PathVariable Long tagId) {
        return tagService.getTagById(tagId);
    }

    @PutMapping("/update/{tagId}")
    public ResponseEntity<?> updateTag(@PathVariable Long tagId, @RequestBody TagDto tagDto) {
        System.out.println("HI");
        return tagService.updateTag(tagId, tagDto);
    }

    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable Long tagId) {
        return tagService.deleteTag(tagId);
    }
}
