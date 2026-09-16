package com.learn.restapipractice.repository;

import com.learn.restapipractice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    List<Student>findBySemesterId(Long semesterId);
    List<Student> findBySemesterIdAndNameContainingIgnoreCase(Long semesterId, String name);
    Optional<Student>findByNameIgnoreCase(String name);
}
