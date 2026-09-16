package com.learn.restapipractice.service;

import com.learn.restapipractice.dto.CourseResponse;
import com.learn.restapipractice.entity.Course;
import com.learn.restapipractice.exception.ResourceAlreadyExistsException;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.repository.CourseRepository;
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
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void addCourse_Success() {
        CourseResponse response = new CourseResponse(1L, "Java", "desc");
        response.setCourseCode("CS101");
        response.setName("Java");

        Course savedCourse = new Course();
        savedCourse.setId(1L);
        savedCourse.setName("Java");
        savedCourse.setCourseCode("CS101");

        when(courseRepository.existsByCourseCode("CS101")).thenReturn(false);
        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        CourseResponse result = courseService.addCourse(response);

        assertEquals("Java", result.getName());
        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void addCourse_AlreadyExists_ThrowsException() {
        CourseResponse response = new CourseResponse(1L, "Java", "desc");
        response.setCourseCode("CS101");

        when(courseRepository.existsByCourseCode("CS101")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class,
                () -> courseService.addCourse(response));
    }

    @Test
    void getCourseById_NotFound_ThrowsException() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> courseService.getCourseById(1L));
    }

    @Test
    void getAllCourses_ReturnsList() {
        Course course = new Course();
        course.setId(1L);
        course.setName("Java");
        course.setCourseCode("CS101");

        when(courseRepository.findAll()).thenReturn(List.of(course));

        List<CourseResponse> result = courseService.getAllCourses();

        assertEquals(1, result.size());
    }
}