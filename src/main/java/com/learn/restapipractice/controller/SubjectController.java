package com.learn.restapipractice.controller;

import com.learn.restapipractice.dto.SubjectDTO;
import com.learn.restapipractice.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/semesters/{semesterId}/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectDTO> addSubject(@PathVariable Long semesterId, @RequestBody @Valid SubjectDTO subjectDTO){
        SubjectDTO subjectDTO1 = service.addSubject(semesterId,subjectDTO);
        return new ResponseEntity<>(subjectDTO1, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<List<SubjectDTO>> getSubjectsBySemester(@PathVariable Long semesterId){
        List<SubjectDTO>subjectDTO = service.getSubjectsBySemester(semesterId);
        return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{subjectId}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResponseEntity<Void>deleteSubject(@PathVariable Long subjectId){
        service.deleteSubject(subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
