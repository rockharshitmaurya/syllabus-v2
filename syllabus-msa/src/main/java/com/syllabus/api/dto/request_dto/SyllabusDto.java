package com.syllabus.api.dto.request_dto;

import jakarta.validation.constraints.NotNull;



public class SyllabusDto {

    Integer syllabus_id;

    @NotNull(message = "Name may not be null")
    String name;

    @NotNull(message = "status may not be null")
    String status;

    @NotNull(message = "Medium may not be null")
    String medium;

    @NotNull(message = "Description may not be null")
    String description;

    public SyllabusDto() {
    }

    public SyllabusDto(Integer syllabus_id, String name, String status, String medium, String description) {
        this.syllabus_id = syllabus_id;
        this.name = name;
        this.status = status;
        this.medium = medium;
        this.description = description;
    }

    public SyllabusDto(String name, String status, String medium, String description) {
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

    public Integer getSyllabus_id() {
        return syllabus_id;
    }

    public void setSyllabus_id(Integer syllabus_id) {
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
