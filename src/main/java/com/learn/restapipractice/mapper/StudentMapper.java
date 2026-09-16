package com.learn.restapipractice.mapper;

import com.learn.restapipractice.dto.StudentDTO;
import com.learn.restapipractice.entity.Student;
import com.learn.restapipractice.entity.Subject;

public class StudentMapper {
    public static Student toEntity(StudentDTO studentDTO) {
        if (studentDTO == null) return null;

        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        return student;
    }

    public static StudentDTO toDTO(Student student) {
        if (student == null) return null;

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setAge(student.getAge());
        if (student.getSemester() != null && student.getSemester().getCourse() != null) {
            // Assuming you added a 'courseName' or similar field to StudentDTO if needed
            // studentDTO.setCourseName(student.getSemester().getCourse().getName());
        }
        return studentDTO;
    }
}
