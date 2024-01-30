package com.syllabus.tagv2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "syllabus_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "syllabus_tag_id")
    private Long syllabusTagId;

    @Column(name = "syllabus_id")
    private Long syllabusId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id", nullable = false)
    private Tag tagId;


}
