package com.learn.restapipractice.controller;
import com.learn.restapipractice.dto.StudentDTO;
import com.learn.restapipractice.entity.Student;
import com.learn.restapipractice.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters/{semesterId}/students")
@RequiredArgsConstructor
public class StudentController {


    private StudentService studentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> enrollStudents(@PathVariable Long semesterId, @RequestBody @Valid StudentDTO studentDTO){
        StudentDTO studentDTO1 = studentService.enrollStudent(semesterId, studentDTO);
        return new ResponseEntity<>(studentDTO1, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<StudentDTO>> getStudentsBySemester(@PathVariable Long semesterId){
        List<StudentDTO> studentDTOList = studentService.getStudentsBySemester(semesterId);
        return new ResponseEntity<>(studentDTOList,HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeStudent(@PathVariable Long studentId){
        studentService.removeStudent(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
