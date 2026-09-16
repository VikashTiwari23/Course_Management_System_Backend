package com.learn.restapipractice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private String courseCode;
    private List<SemesterDTO> semesters;

    public CourseResponse(long l, String java, String desc) {
    }
}
