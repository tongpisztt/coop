package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentRepositoryTest {

    @Mock
    private StudentRepository studentRepository;
    //เป็น annotation ที่ระบุใช้เป็น field สำหรับทดสอบ ซึ่งจะรัน method ที่ถูกเรียกจริงๆ

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    //---GoodCase---------------------------------------------------------------------------------------------------------------
    @Test
    public void findBystdID() {

        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);

        when(studentRepository.findBystdID(eq(s1.getStdID()))).thenReturn(s1);
        when(studentRepository.findBystdID(eq(s2.getStdID()))).thenReturn(s2);

        Student foundedStudent1 = studentRepository.findBystdID(s1.getStdID());
        Student foundedStudent2 = studentRepository.findBystdID(s2.getStdID());

        assertEquals(s1.getId(), foundedStudent1.getId());
        assertEquals(s1.getFName(), foundedStudent1.getFName());
        assertEquals(s2.getId(), foundedStudent2.getId());
        assertEquals(s2.getFName(), foundedStudent2.getFName());
    }

    @Test
    public void save() {

        Student s = new Student(5L, 62005, "Prayuth", "Jundara", 100);

        when(studentRepository.save(eq(s))).thenReturn(s);

        Student savedStudent = studentRepository.save(s);

        assertEquals(s.getId(), savedStudent.getId());
        assertEquals(s.getFName(), savedStudent.getFName());

    }

    @Test
    public void findAll() {

        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);

        List expectedList = Arrays.asList(s1, s2);

        when(studentRepository.findAll()).thenReturn(expectedList);

        List<Student> studentList = studentRepository.findAll();

        assertEquals(studentList.get(0).getId(), s1.getId());
        assertEquals(studentList.get(0).getFName(), s1.getFName());
        assertEquals(studentList.get(1).getId(), s2.getId());
        assertEquals(studentList.get(1).getFName(), s2.getFName());

    }

    @Test
    public void deleteById() {
        int requestStdId = 62005;
        Student s = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        List<Student> studentList = new ArrayList<>();
        when(studentRepository.findBystdID(eq(requestStdId))).thenReturn(s);
        when(studentRepository.findAll()).thenReturn(studentList);

        studentRepository.deleteById(5L);

        assertEquals(studentList, studentRepository.findAll());

    }
}
