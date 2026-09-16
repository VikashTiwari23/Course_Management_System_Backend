package com.learn.restapipractice.service;


import com.learn.restapipractice.dto.CourseResponse;
import com.learn.restapipractice.entity.Course;
import com.learn.restapipractice.exception.ResourceAlreadyExistsException;
import com.learn.restapipractice.exception.ResourceNotFoundException;
import com.learn.restapipractice.mapper.CourseMapper;
import com.learn.restapipractice.repository.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseResponse addCourse(CourseResponse courseResponse){
        if(courseRepository.existsByCourseCode(courseResponse.getCourseCode())){
            throw new ResourceAlreadyExistsException("Course "+courseResponse.getCourseCode()+" already exists");
        }
        Course course = CourseMapper.toCourse(courseResponse);
        Course savedCourse =  courseRepository.save(course);
        return CourseMapper.toCourseResponse(savedCourse);
    }

    @Transactional(readOnly=true)
    public List<CourseResponse> getAllCourses(){
        return courseRepository.findAll().stream().map(CourseMapper::toCourseResponse).collect(Collectors.toList());
    }

    public CourseResponse getCourseById(Long id){
        Course course = courseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Course "+id+" not found"));
        return CourseMapper.toCourseResponse(course);
    }

    public CourseResponse updateCourse(Long id ,CourseResponse courseResponse){
        Course course = courseRepository.findById(id).get();
        if(courseResponse.getDescription()!=null){
            course.setDescription(courseResponse.getDescription());
        }
        else if(courseResponse.getCourseCode()!=null){
            course.setCourseCode(courseResponse.getCourseCode());
        }
        else if(courseResponse.getName()!=null){
            course.setName(courseResponse.getName());
        }
        courseRepository.save(course);
        return CourseMapper.toCourseResponse(course);
    }

    public void deleteCourse(Long id){
        courseRepository.deleteById(id);
    }
}
