package com.learn.restapipractice.repository;

import com.learn.restapipractice.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    List<Subject> findBySemesterId(Long semesterId);
    Optional<Subject> findBySemesterIdAndSubjectNameIgnoreCase(Long semesterId, String subjectName);
    boolean existsBySemesterIdAndSubjectNameIgnoreCase(Long semesterId, String subjectName);
    List<Subject> findBySubjectNameContainingIgnoreCase(String subjectName);
}
