package com.learn.restapipractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="semesters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Semester {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int semesterNo;

    private String semsterName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Student> studentList;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Subject> subjectList;

}
