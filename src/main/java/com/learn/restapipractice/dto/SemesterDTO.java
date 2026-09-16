package com.learn.restapipractice.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class SemesterDTO {
    private Long id;
    private int semesterNo;
    private String semesterName;
    private String courseName;
    private List<SubjectDTO> subjects;
    private List<StudentDTO> studentList;
}
