package com.learn.restapipractice.mapper;

import com.learn.restapipractice.dto.CourseResponse;
import com.learn.restapipractice.entity.Course;

import java.util.stream.Collectors;

public class CourseMapper {
    public static Course toCourse(CourseResponse courseResponse){
        if(courseResponse==null) return null;
        Course course = new Course();
        course.setName(courseResponse.getName());
        course.setDescription(course.getDescription());
        course.setCourseCode(courseResponse.getCourseCode());
        return course;
    }

    public static CourseResponse toCourseResponse(Course course){
        if (course == null) return null;
        CourseResponse courseResponse = new CourseResponse(1L, "Java", "desc");
        courseResponse.setId(course.getId());
        courseResponse.setName(course.getName());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setCourseCode(course.getCourseCode());
        if(course.getSemesters()!=null){
            courseResponse.setSemesters(course.getSemesters().stream().map(SemesterMapper::toDTO).collect(Collectors.toList()));
        }

        return courseResponse;
    }
}
