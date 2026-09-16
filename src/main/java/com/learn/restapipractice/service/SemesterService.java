package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.SemesterDTO;
import com.learn.restapipractice.entity.Course;
import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.exception.ResourceAlreadyExistsException;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.mapper.SemesterMapper;
import com.learn.restapipractice.repository.CourseRepository;
import com.learn.restapipractice.repository.SemesterRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SemesterService {
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    public SemesterDTO addSemester(Long courseId,SemesterDTO semesterDTO){
        Course course = courseRepository.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course not found with this course id"+courseId));
        if(semesterRepository.existsByCourseIdAndSemesterNo(courseId,semesterDTO.getSemesterNo())){
            throw new ResourceAlreadyExistsException("Semester"+semesterDTO.getSemesterNo()+" already exists");

        }
        Semester semester = SemesterMapper.toEntity(semesterDTO);
        semester.setCourse(course);
        Semester savedSemester  = semesterRepository.save(semester);
        return SemesterMapper.toDTO(savedSemester);
    }
    @Transactional(readOnly = true)
    public List<SemesterDTO> getSemesterByCourse(Long courseId){
        return semesterRepository.findByCourseId(courseId).stream().map(SemesterMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SemesterDTO getSemesterById(Long semesterId){
        Semester semester = semesterRepository.findById(semesterId).orElseThrow(()->new ResourceNotFoundException("Semester not found with this semesterId"+semesterId));
        return SemesterMapper.toDTO(semester);
    }
}
