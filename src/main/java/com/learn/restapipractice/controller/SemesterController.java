package com.learn.restapipractice.controller;

import com.learn.restapipractice.dto.SemesterDTO;
import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.repository.SemesterRepository;
import com.learn.restapipractice.service.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/semesters")
@RequiredArgsConstructor
public class SemesterController {
    private final SemesterService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SemesterDTO>addSemester(@PathVariable Long courseId, @RequestBody @Valid SemesterDTO semesterDTO){
        SemesterDTO semesterDTO1 = service.addSemester(courseId,semesterDTO);
        return new ResponseEntity<>(semesterDTO1, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<SemesterDTO>> getSemestersByCourse(@PathVariable Long courseId){
        List<SemesterDTO> semesterDTO1 = service.getSemesterByCourse(courseId);
        return new ResponseEntity<>(semesterDTO1, HttpStatus.OK);
    }

    @GetMapping("/{semsterId}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<SemesterDTO> getSemesterById(@PathVariable Long semsterId){
        SemesterDTO semesterDTO = service.getSemesterById(semsterId);
        return new ResponseEntity<>(semesterDTO, HttpStatus.OK);
    }
}
