package com.learn.restapipractice.repository;

import com.learn.restapipractice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameIgnoreCase(String name);
    Optional<Course> findByCourseCode(String courseCode);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByCourseCode(String courseCode);
    boolean existsByName(String str);
}
