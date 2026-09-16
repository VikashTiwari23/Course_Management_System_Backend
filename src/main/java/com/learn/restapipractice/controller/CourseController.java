package com.learn.restapipractice.controller;


import com.learn.restapipractice.dto.CourseResponse;
import com.learn.restapipractice.entity.Course;
import com.learn.restapipractice.repository.CourseRepository;
import com.learn.restapipractice.service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseResponse courseResponse){
        CourseResponse courseResponse1 = courseService.addCourse(courseResponse);
        return new ResponseEntity<>(courseResponse1, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        List<CourseResponse> courseResponse1 = courseService.getAllCourses();
        return new ResponseEntity<>(courseResponse1, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id){
        CourseResponse courseResponse = courseService.getCourseById(id);
        return  new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }
}
