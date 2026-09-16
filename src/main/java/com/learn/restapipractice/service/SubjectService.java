package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.SemesterDTO;
import com.learn.restapipractice.dto.SubjectDTO;
import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.entity.Subject;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.mapper.SubjectMapper;
import com.learn.restapipractice.repository.CourseRepository;
import com.learn.restapipractice.repository.SemesterRepository;
import com.learn.restapipractice.repository.StudentRepository;
import com.learn.restapipractice.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectService {
    private final SemesterRepository  semesterRepository;
    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;

    public SubjectDTO addSubject(Long semesterId,SubjectDTO subjectDTO){
        Semester semester = semesterRepository.findById(semesterId).orElseThrow(()->new ResourceNotFoundException("semester not found "+semesterId));
        Subject subject = SubjectMapper.toEntity(subjectDTO);
        subject.setSemester(semester);
        Subject savedSubject =  subjectRepository.save(subject);
        return SubjectMapper.toDTO(savedSubject);
    }

    @Transactional(readOnly=true)
    public List<SubjectDTO>getSubjectsBySemester(Long semesterId){
        return subjectRepository.findBySemesterId(semesterId).stream().map(SubjectMapper::toDTO).collect(Collectors.toList());
    }

    public void deleteSubject(Long subjectId){
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new ResourceNotFoundException("Subject not found "+subjectId));
        subjectRepository.delete(subject);
    }
}
