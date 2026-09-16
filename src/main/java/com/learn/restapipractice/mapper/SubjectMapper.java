package com.learn.restapipractice.mapper;

import com.learn.restapipractice.dto.SubjectDTO;
import com.learn.restapipractice.entity.Subject;

public class SubjectMapper {
    public static SubjectDTO toDTO(Subject subject){
        if(subject==null) return null;
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setSubjectName(subject.getSubjectName());
        subjectDTO.setCredits(subject.getCredits());
        if(subject.getSemester()!=null){
            subjectDTO.setSemesterNo(subject.getSemester().getSemesterNo());
        }
        return subjectDTO;
    }

    public static Subject toEntity(SubjectDTO subjectDTO){
        if(subjectDTO==null) return null;
        Subject subject = new Subject();
        subject.setId(subjectDTO.getId());
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setCredits(subjectDTO.getCredits());
        return subject;
    }
}
