package com.learn.restapipractice.service;


import com.learn.restapipractice.dto.StudentDTO;
import com.learn.restapipractice.entity.Semester;
import com.learn.restapipractice.entity.Student;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.repository.SemesterRepository;
import com.learn.restapipractice.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest{
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SemesterRepository semesterRepository;
    @InjectMocks
    private StudentService studentService;

    // Test 1 Student Enrolled
    @Test
    public void enroll_student_success(){
        Semester semester = new Semester();
        semester.setId(1L);
        semester.setSemesterNo(1);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Vikash");
        studentDTO.setAge(19);

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Vikash");
        savedStudent.setSemester(semester);
        savedStudent.setAge(studentDTO.getAge());

        when(semesterRepository.findById(1L)).thenReturn(Optional.of(semester));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);
        StudentDTO result = studentService.enrollStudent(1L,studentDTO);

        assertEquals("Vikash",result.getName());
        assertEquals(19,result.getAge());
        assertEquals(1L,result.getId());

        verify(studentRepository,times(1)).save(any(Student.class));
    }

    // Test 2 Student Don't Enrolled due to wrong Semester not found

    @Test
    public void enroll_fails_semester_NotFound_ThrowsException(){
        when(semesterRepository.findById(99L)).thenReturn(Optional.empty());
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Vikash");
        studentDTO.setAge(19);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,()->studentService.enrollStudent(99L,studentDTO));
        assertTrue(exception.getMessage().contains("99"));
        verify(studentRepository,never()).save(any(Student.class));
    }

    @Test
    public void get_List_of_Students_Success(){
        Semester semester = new Semester();
        semester.setId(1L);
        semester.setSemesterNo(1);

        Student student1 = new Student(1L,"Vikash",19,null,semester,null);
        Student student2 = new Student(2L,"Vikash2",20,null,semester,null);

        when(studentRepository.findBySemesterId(semester.getId())).thenReturn(List.of(student1,student2));
        List<StudentDTO> studentDTOList = studentService.getStudentsBySemester(semester.getId());

        assertEquals(2,studentDTOList.size());
        assertEquals("Vikash",studentDTOList.get(0).getName());
        assertEquals("Vikash2",studentDTOList.get(1).getName());
    }

    @Test
    public void get_StudentsBySemester_EmptyList(){
        when(studentRepository.findBySemesterId(1L)).thenReturn(List.of());
        List<StudentDTO>studentDTOList = studentService.getStudentsBySemester(1L);
        assertNotNull(studentDTOList);
        assertEquals(0,studentDTOList.size());
    }

    @Test
    public void removeStudent_success(){
        Student student = new Student(1L,"Vikash",19,null,null,null);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        assertDoesNotThrow(()->studentService.removeStudent(1L));
        verify(studentRepository,times(1)).delete(student);
    }

    @Test
    public void removeStudent_ThrowsException(){
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->studentService.removeStudent(1L));
        verify(studentRepository,never()).delete(any(Student.class));
    }
}