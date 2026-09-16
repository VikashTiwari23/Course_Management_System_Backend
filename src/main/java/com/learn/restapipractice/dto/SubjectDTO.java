package com.learn.restapipractice.dto;

import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.entity.Subject;
import lombok.Data;

import java.util.List;
@Data
public class SubjectDTO {
    private Long id;
    private int semesterNo;
    private String subjectName;
    private int credits;
}
