package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findBystdID(Integer stdID);
    void deleteById(Long id);
}