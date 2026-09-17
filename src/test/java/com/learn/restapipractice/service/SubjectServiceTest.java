package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.SubjectDTO;
import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.entity.Subject;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.repository.CourseRepository;
import com.learn.restapipractice.repository.SemesterRepository;
import com.learn.restapipractice.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SemesterRepository semesterRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService service;

    @Test
    void addSubject_Success() {
        Semester semester = new Semester();
        semester.setSemesterNo(1);
        semester.setId(1L);

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectName("Java");
        subjectDTO.setCredits(5);

        Subject savedSubject = new Subject();
        savedSubject.setSubjectName("Java");
        savedSubject.setCredits(5);
        savedSubject.setSemester(semester);

        when(semesterRepository.findById(1L)).thenReturn(Optional.of(semester));
        when(subjectRepository.save(any(Subject.class))).thenReturn(savedSubject);
        SubjectDTO result = service.addSubject(1L,subjectDTO);
        assertEquals("Java",result.getSubjectName());
        assertEquals(5,result.getCredits());
        assertEquals(1,result.getSemesterNo());

        verify(subjectRepository,times(1)).save(any(Subject.class));
    }

    @Test
    void addSubject_SemesterNotFound_ThrowsException() {
        when(semesterRepository.findById(1L)).thenReturn(Optional.empty());

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setSubjectName("Java");
        subjectDTO.setCredits(5);
        subjectDTO.setSemesterNo(1);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()->service.addSubject(1L,subjectDTO));
        assertTrue(exception.getMessage().contains("1"));
        verify(subjectRepository,never()).save(any(Subject.class));
    }

    @Test
    void getSubjectsBySemester_ReturnsList() {
        // Create subjects that the fake repo will return
        Semester semester = new Semester();
        semester.setId(1L);
        semester.setSemesterNo(1);

        Subject sub1 = new Subject();
        sub1.setId(1L);
        sub1.setSubjectName("Java");
        sub1.setCredits(4);
        sub1.setSemester(semester);

        Subject sub2 = new Subject();
        sub2.setId(2L);
        sub2.setSubjectName("SQL");
        sub2.setCredits(3);
        sub2.setSemester(semester);

        when(subjectRepository.findBySemesterId(1L)).thenReturn(List.of(sub1, sub2));

        List<SubjectDTO> result = service.getSubjectsBySemester(1L);

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getSubjectName());
        assertEquals("SQL", result.get(1).getSubjectName());
        assertEquals(4, result.get(0).getCredits());
        assertEquals(3, result.get(1).getCredits());
    }

    @Test
    void getSubjectsBySemester_EmptyList() {
        when(subjectRepository.findBySemesterId(1L)).thenReturn(List.of());

        List<SubjectDTO> result = service.getSubjectsBySemester(1L);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void deleteSubject_Success() {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setSubjectName("Java");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        assertDoesNotThrow(() -> service.deleteSubject(1L));
        verify(subjectRepository, times(1)).delete(subject);
    }

    @Test
    void deleteSubject_NotFound_ThrowsException() {
        when(subjectRepository.findById(99L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.deleteSubject(99L)
        );
        assertTrue(exception.getMessage().contains("99"));
        verify(subjectRepository, never()).delete(any(Subject.class));
    }
}
