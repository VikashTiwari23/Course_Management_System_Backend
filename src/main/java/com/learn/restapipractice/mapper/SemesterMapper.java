package com.learn.restapipractice.mapper;

import com.learn.restapipractice.dto.SemesterDTO;
import com.learn.restapipractice.entity.Semester;

import java.util.stream.Collectors;

public class SemesterMapper {
    public static Semester toEntity(SemesterDTO semesterDTO){
        if(semesterDTO==null) return null;
        Semester semester = new Semester();
        semester.setSemesterNo(semesterDTO.getSemesterNo());
        semester.setSemsterName(semesterDTO.getSemesterName());
        if (semester.getSubjectList() != null) {
            semesterDTO.setSubjects(
                    semester.getSubjectList().stream()
                            .map(SubjectMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }
        if(semesterDTO.getStudentList()!=null){
            semester.setStudentList(semesterDTO.getStudentList().stream().map(StudentMapper::toEntity).collect(Collectors.toList()));
        }
        return semester;
    }

    public static SemesterDTO toDTO(Semester semester){
        if (semester == null) return null;
        SemesterDTO semesterDTO = new SemesterDTO();
        semesterDTO.setId(semester.getId());
        semesterDTO.setSemesterNo(semester.getSemesterNo());
        semesterDTO.setSemesterName(semester.getSemsterName());
        if(semester.getCourse()!=null){
            semesterDTO.setCourseName(semester.getCourse().getName());
        }
        if (semester.getSubjectList() != null) {
            semesterDTO.setSubjects(
                    semester.getSubjectList().stream()
                            .map(SubjectMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        if (semester.getStudentList() != null) {
            semesterDTO.setStudentList(
                    semester.getStudentList().stream()
                            .map(StudentMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }
        return semesterDTO;
    }
}
