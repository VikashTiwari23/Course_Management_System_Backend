package com.learn.restapipractice.repository;

import com.learn.restapipractice.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester,Long> {
    List<Semester> findByCourseId(Long courseId);
    Optional<Semester> findByCourseIdAndSemesterNo(Long courseId, Integer semesterNo);
    boolean existsByCourseIdAndSemesterNo(Long courseId,int SemesterNo);
}
