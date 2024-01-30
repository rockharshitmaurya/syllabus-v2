package com.syllabus.api.entity;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="syllabus")
public class Syllabus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="syllabus_id")
    Long syllabus_id;

    @Column
    String name;

    @Column
    String status;

    @Column
    String medium;

    @Column
    String description;

    public Syllabus() {
    }

    public Syllabus(Long syllabus_id, String name, String status, String medium, String description) {
        this.syllabus_id = syllabus_id;
        this.name = name;
        this.status = status;
        this.medium = medium;
        this.description = description;
    }

    public Syllabus(String name, String status, String medium, String description) {
        this.name = name;
        this.status = status;
        this.medium = medium;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Syllabus{" +
                "syllabus_id=" + syllabus_id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", medium='" + medium + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getSyllabus_id() {
        return syllabus_id;
    }

    public void setSyllabus_id(Long syllabus_id) {
        this.syllabus_id = syllabus_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}