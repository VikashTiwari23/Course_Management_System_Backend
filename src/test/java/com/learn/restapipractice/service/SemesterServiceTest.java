package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.CourseResponse;
import com.learn.restapipractice.dto.SemesterDTO;
import com.learn.restapipractice.entity.Course;
import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.exception.ResourceAlreadyExistsException;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.repository.CourseRepository;
import com.learn.restapipractice.repository.SemesterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// This tells JUnit to use Mockito for creating fake objects
@ExtendWith(MockitoExtension.class)
class SemesterServiceTest {


    @Mock
    private SemesterRepository semesterRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SemesterService service;

    @Test
    void addSemester_Success() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseCode("BCA123");
        course.setName("BCA");

        SemesterDTO semesterDTO = new SemesterDTO();
        semesterDTO.setSemesterNo(1);
        semesterDTO.setSemesterName("BCA semester");

        Semester savedSemester = new Semester();
        savedSemester.setSemesterNo(1);
        savedSemester.setSemsterName("BCA semester");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepository.existsByCourseIdAndSemesterNo(1L,1)).thenReturn(false);
        when(semesterRepository.save(any(Semester.class))).thenReturn(savedSemester);
        SemesterDTO result = service.addSemester(1L,semesterDTO);
        assertEquals(1,result.getSemesterNo());
        assertEquals("BCA semester",result.getSemesterName());
        verify(courseRepository,times(1)).findById(1L);
        verify(semesterRepository,times(1)).save(any(Semester.class));
    }


    @Test
    void addSemester_CourseNotFound_ThrowsException() {
        SemesterDTO semesterDTO = new SemesterDTO();
        semesterDTO.setSemesterNo(1);
        semesterDTO.setId(1L);
        semesterDTO.setSemesterName("BCA semester");

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()->service.addSemester(1L,semesterDTO));
        assertTrue(exception.getMessage().contains("1"));
        verify(semesterRepository,never()).save(any(Semester.class));
    }

    @Test
    void addSemester_SemesterAlreadyExists_ThrowsException() {
        Course course = new Course();
        course.setId(1L);
        course.setName("BCA");
        course.setCourseCode("BCA123");

        SemesterDTO semesterDTO = new SemesterDTO();
        semesterDTO.setSemesterNo(1);
        semesterDTO.setSemesterName("BCA semester");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(semesterRepository.existsByCourseIdAndSemesterNo(1L, 1)).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class,()->service.addSemester(1L,semesterDTO));
        assertTrue(exception.getMessage().contains("1"));
        verify(semesterRepository,never()).save(any(Semester.class));
    }

    @Test
    void getSemesterByCourse_ReturnsList() {
        Course course = new Course();
        course.setId(1L);
        course.setName("BCA");
        course.setCourseCode("BCA123");

        Semester semester1 = new Semester();
        semester1.setSemesterNo(1);
        semester1.setSemsterName("BCA semester1");
        semester1.setCourse(course);

        Semester semester2 = new Semester();
        semester2.setCourse(course);
        semester2.setSemesterNo(2);
        semester2.setSemsterName("BCA semester2");

        when(semesterRepository.findByCourseId(1L)).thenReturn(List.of(semester1,semester2));
        List<SemesterDTO>result = service.getSemesterByCourse(1L);
        assertEquals(2,result.size());
        assertEquals("BCA semester1",result.get(0).getSemesterName());
        assertEquals("BCA semester2",result.get(1).getSemesterName());
        assertEquals(1,result.get(0).getSemesterNo());
        assertEquals(2,result.get(1).getSemesterNo());
        verify(semesterRepository,times(1)).findByCourseId(1L);
    }

    @Test
    void getSemesterByCourse_EmptyList() {
        when(semesterRepository.findByCourseId(1L)).thenReturn(List.of());
        List<SemesterDTO> result = service.getSemesterByCourse(1L);
        assertNotNull(result);
        assertEquals(0,result.size());
     }

    @Test
    void getSemesterById_Success() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseCode("BCA123");
        course.setName("BCA");

        Semester semester = new Semester();
        semester.setSemesterNo(1);
        semester.setSemsterName("BCA semester");
        semester.setId(1L);
        semester.setCourse(course);

        when(semesterRepository.findById(1L)).thenReturn(Optional.of(semester));

        SemesterDTO semesterDTO = service.getSemesterById(1L);
        assertEquals(1,semesterDTO.getSemesterNo());
        assertEquals("BCA semester",semesterDTO.getSemesterName());
        assertEquals("BCA",semesterDTO.getCourseName());
    }

    @Test
    void getSemesterById_NotFound_ThrowsException() {
       when(semesterRepository.findById(1L)).thenReturn(Optional.empty());
       ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()->service.getSemesterById(1L));
       assertTrue(exception.getMessage().contains("1"));
    }
}
