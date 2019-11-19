package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.StudentRequest;
import com.example.demo.repository.StudentRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.constant.StudentConstant.DELETE_SUCCESSFULLY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@SpringBootTest
//@RunWith(JUnit4.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
/* annotation ที่บอกว่าคลาสนี้จะใช้ MockitoJUnitRunner ในการทดสอบ
ใช้งาน Mockito ได้เลยแต่เปลี่ยนจาก @RunWith เป็น @ExtendWith ซึ่งเป็นการขยายความสามารถของ TestEngine ของ JUnit Platform ด้วย MockitoExtension
แล้วก็สามารถใช้คุณสมบัติเดิมของ Mockito ได้เลย อย่างเช่น @Mock, @InjectMocks , @Spy */
//@DisplayName("Demo Spring Boot with JUnit 5 + Mockito")
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    //เป็น annotation ที่ระบุว่าจะ mock field นี้ของคลาสทดสอบ ซึ่งเราจะกำหนดว่าจะ mock method ไหนบ้างใน test method

    @InjectMocks
    private StudentService studentService;
    //เป็น annotation ที่ระบุใช้เป็น field สำหรับทดสอบ ซึ่งจะรัน method ที่ถูกเรียกจริงๆ

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    //---GoodCase---------------------------------------------------------------------------------------------------------------
    @Test
    public void showAllStudents() throws BusinessException {
        Student expected1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student expected2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);
        List expectedList = Arrays.asList(expected1, expected2);
        when(studentRepository.findAll()).thenReturn(expectedList);

        List actualList = studentService.showAllStudents();
        assertAll("expected",
                () -> assertEquals(expectedList, actualList)
        );
    }

    @Test
    //กำหนดให้ method เป็น test method ของ JUnit
    public void showStudent() throws BusinessException {
        int studentId = 62004;    //studentId ที่เรารู้ตั้งแต่แรกว่าใส่อะไรไป แล้วจะเอาไปเทียบกับที่บันทึกลง DB
        Student expected = new Student(4L, 62004, "Pareena", "Kraijub", 60);
        when(studentRepository.findBystdID(anyInt())).thenReturn(expected);    //เมื่อส่ง studentId = 62005 studentRepository.findBystdID แล้วจะ

        Student actual = studentService.showStudent(studentId);        //obj ที่ได้จากการส่ง Student ID = 62005 ตรงๆ เข้า showStudent()

        assertEquals(expected.getId(), actual.getId());    //assertEquals(ซ้าย:expected,ขวา:actual)
        assertEquals(expected.getStdID(), actual.getStdID());
        assertEquals(expected.getFName(), actual.getFName());
        assertEquals(expected.getLName(), actual.getLName());
        assertEquals(expected.getAge(), actual.getAge());
    }

    @Test
    public void saveStudent() throws BusinessException {
        StudentRequest expectedRequest = new StudentRequest(7L, 62007, "Thanathon", "Juangroongruangkit", 40);
        Student expectedStudent = new Student(62007, "Thanathon", "Juangroongruangkit", 40);

        when(studentRepository.save(any(Student.class))).thenReturn(expectedStudent);
        Student actual = studentService.saveStudent(expectedRequest);
        //actual = ค่าจริงที่ได้จากการเรียกใช้งาน service

        assertEquals(expectedStudent.getId(), actual.getId());
        assertEquals(expectedStudent.getStdID(), actual.getStdID());
        assertEquals(expectedStudent.getFName(), actual.getFName());
        assertEquals(expectedStudent.getLName(), actual.getLName());
        assertEquals(expectedStudent.getAge(), actual.getAge());
    }

    @Test
    public void updateStudentFirstName() throws BusinessException {
        Integer stdID = 62004;
        String newFname = "Phanika";
        Student expected = new Student(4L, 62004, "Pareena", "Kraijub", 60);

        when(studentRepository.findBystdID(anyInt())).thenReturn(expected);    //mockข้อมูลเมื่อมีการเรียกใช้ในmothodในserviceนั้นๆ
        when(studentRepository.save(any(Student.class))).thenReturn(expected);
        Student actual = studentService.updateStudentFirstName(stdID, newFname);

        assertEquals(expected.getId(), actual.getId());
        assertEquals("Phanika", actual.getFName());

    }

    @Test
    public void deleteStudent() throws BusinessException {
        Student expected = new Student(4L, 62004, "Pareena", "Kraijub", 60);

        when(studentRepository.findBystdID(expected.getStdID())).thenReturn(expected);

        String actual = studentService.deleteStudent(expected.getStdID());

        assertEquals(DELETE_SUCCESSFULLY, actual);
    }

    //---WorstCase-CaseNotFound---------------------------------------------------------------------------------------------------------------

    /*@Test   //OK ทำแบบนี้ก็ได้
    public void showAllStudentsCaseNotFoundAnyStudent() throws BusinessException {
        //Student mockStudent = new Student(4L, 62004, "Pareena", "Kraijub", 60);   //ถ้าลองใส่ Student ไป แล้ว list.add(mockStudent); ต้องไม่มีการ throws อะไรออกมา
        List list = new ArrayList();
        //list.add(mockStudent);

        when(studentRepository.findAll()).thenReturn(list);
        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.showAllStudents());

        assertEquals(ERROR_NOT_FOUND_ANY_STUDENT_IN_DB, exception.getMessage());
        assertEquals(10003, exception.getCode());
    }*/

    @Test(expected = BusinessException.class)
    public void showAllStudentsCaseNotFoundAnyStudent() throws BusinessException {
        //Student mockStudent = new Student(4L, 62004, "Pareena", "Kraijub", 60);   //ถ้าลองใส่ Student ไป แล้ว list.add(mockStudent); ต้องไม่มีการ throws อะไรออกมา
        List list = new ArrayList();
        //list.add(mockStudent);
        when(studentRepository.findAll()).thenReturn(list);
        try {
            studentService.showAllStudents();
        } catch (BusinessException e) {
            System.out.println("BusinessException works!");
            String errorMessage = String.format("Error code %d : %s", e.getCode(), e.getMessage());
            System.out.println(errorMessage);
            throw e;
        }
    }

    @Test(expected = BusinessException.class)       //OK
    public void updateStudentFirstNameCaseNotFoundStudent() throws BusinessException {
        String newFname = "XXXXX";
        Integer requestStdIdOutOfDB = 62001;
        Integer requestStdIdInDB = 62004;
        Student studentInDB = new Student(4L, 62004, "Pareena", "Kraijub", 60);

        //when(studentRepository.findBystdID(eq(requestNotRepeatStdId))).thenReturn(null);
        when(studentRepository.findBystdID(eq(requestStdIdInDB))).thenReturn(studentInDB);
        try {
            studentService.updateStudentFirstName(requestStdIdOutOfDB, newFname); //62001 ไม่มีใน DB
            //studentService.updateStudentFirstName(requestStdIdInDB, newFname);  //62004 มีใน DB จะ error เพราะไม่เกิด BusinessException
        } catch (BusinessException e) {
            System.out.println("BusinessException works!");
            String errorMessage = String.format("Error code %d : %s", e.getCode(), e.getMessage());
            System.out.println(errorMessage);
            throw e;
        }
    }

    @Test(expected = BusinessException.class)       //OK
    public void deleteStudentNotFoundStudent() throws BusinessException {

        Integer requestStdIdOutOfDB = 62001;
        Integer requestStdIdInDB = 62004;
        Student studentInDB = new Student(4L, 62004, "Pareena", "Kraijub", 60);

        //when(studentRepository.findBystdID(eq(requestNotRepeatStdId))).thenReturn(null);
        when(studentRepository.findBystdID(eq(requestStdIdInDB))).thenReturn(studentInDB);
        try {
            studentService.deleteStudent(requestStdIdOutOfDB); //62001 ไม่มีใน DB
            //studentService.deleteStudent(requestStdIdInDB);  //62004 มีใน DB จะ error เพราะไม่เกิด BusinessException
        } catch (BusinessException e) {
            System.out.println("BusinessException works!");
            String errorMessage = String.format("Error code %d : %s", e.getCode(), e.getMessage());
            System.out.println(errorMessage);
            throw e;
        }
    }

    //--WorstCase-CaseExisting---------------------------------------------------------------------------------------------------------------
    @Test(expected = BusinessException.class)   //OK
    public void saveStudentCaseExisting() throws BusinessException {
        Student studentInDB = new Student(4L, 62004, "Pareena", "Kraijub", 60);
        StudentRequest request = new StudentRequest(62004, "AAA", "BBB", 40);
        //StudentRequest request = new StudentRequest(62005, "AAA", "BBB", 40); //ถ้าเปลี่ยนเป็น 62005 จะ error เพราะ ไม่เจอ BusinessException

        when(studentRepository.findBystdID(eq(request.getStdID()))).thenReturn(studentInDB);    //mockข้อมูลเมื่อมีการเรียกใช้ในmothodในserviceนั้นๆ

        studentService.saveStudent(request);

        verify(studentRepository, times(1)).findBystdID(anyInt());
    }


}

