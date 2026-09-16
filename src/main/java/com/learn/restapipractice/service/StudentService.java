package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.StudentDTO;
import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.entity.Student;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.mapper.StudentMapper;
import com.learn.restapipractice.repository.SemesterRepository;
import com.learn.restapipractice.repository.StudentRepository;
import jakarta.transaction.TransactionScoped;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;

    public StudentDTO enrollStudent(Long semesterId,StudentDTO studentDTO){
        Semester semester =  semesterRepository.findById(semesterId).orElseThrow(()->new ResourceNotFoundException("semester not found "+semesterId));
        Student student = StudentMapper.toEntity(studentDTO);
        student.setSemester(semester);
        Student save = studentRepository.save(student);
        return  StudentMapper.toDTO(save);
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> getStudentsBySemester(Long semesterId){
        return studentRepository.findBySemesterId(semesterId).stream().map(StudentMapper::toDTO).collect(Collectors.toList());
    }

    public void removeStudent(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        studentRepository.delete(student);
    }
}
