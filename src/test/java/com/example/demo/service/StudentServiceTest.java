package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.model.StudentRequest;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
/* annotation ที่บอกว่าคลาสนี้จะใช้ MockitoJUnitRunner ในการทดสอบ
ใช้งาน Mockito ได้เลยแต่เปลี่ยนจาก @RunWith เป็น @ExtendWith ซึ่งเป็นการขยายความสามารถของ TestEngine ของ JUnit Platform ด้วย MockitoExtension
แล้วก็สามารถใช้คุณสมบัติเดิมของ Mockito ได้เลย อย่างเช่น @Mock, @InjectMocks , @Spy */
//@DisplayName("Demo Spring Boot with JUnit 5 + Mockito")
class StudentServiceTest {

	//private Validator validator;

	@Mock //เป็น annotation ที่ระบุว่าจะ mock field นี้ของคลาสทดสอบ ซึ่งเราจะกำหนดว่าจะ mock method ไหนบ้างใน test method
	private StudentRepository studentRepository;

	@InjectMocks //เป็น annotation ที่ระบุใช้เป็น field สำหรับทดสอบ ซึ่งจะรัน method ที่ถูกเรียกจริงๆ
	private StudentService studentService;

	//Long.valueOf(int) หรือ L<int> ก็ได้
    private final Student expected = new Student(4L,62004, "Pareena", "Kraijub", 60);
    private final Student expected1 = new Student(5L,62005, "Prayuth", "Jundara", 100);
    private final Student expected2 = new Student(6L,62006, "Prawit", "Wongkumlao", 90);


	@BeforeEach
	void setUp() {

	}
    //---Good---------------------------------------------------------------------------------------------------------------
    /*@Test
    void showAllStudents() {
        //Student student = new Student(62005, "Prayuth", "Jundara", 100);
        int studentId1 = 62004;	//studentId ที่เรารู้ตั้งแต่แรกว่าใส่อะไรไป แล้วจะเอาไปเทียบกับที่บันทึกลง DB
        int studentId2 = 62006;

        when(studentRepository.findAll()).thenReturn(Arrays.asList(expected1, expected2));

        List actual = Arrays.asList(studentService.showAllStudents());

        assertAll("expected",
                () -> assertEquals(expected, actual)
        );

    }*/

    @Test
    void showAllStudents() {

        List studentsList = Arrays.asList(expected1,expected2);
        List expectedList = Arrays.asList(studentsList);
        when(studentRepository.findAll()).thenReturn(studentsList);

        //Student actualList = studentService.showAllStudents();
        assertAll("expected",
                () -> assertEquals(expectedList, studentService.showAllStudents())
        );
    }

	@Test //กำหนดให้ method เป็น test method ของ JUnit
	void showStudent() {
		int studentId = 62004;	//studentId ที่เรารู้ตั้งแต่แรกว่าใส่อะไรไป แล้วจะเอาไปเทียบกับที่บันทึกลง DB
		when(studentRepository.findBystdID(eq(studentId))).thenReturn(expected);	//เมื่อส่ง studentId = 62005 studentRepository.findBystdID แล้วจะ

		Student actual = studentService.showStudent(studentId);		//obj ที่ได้จากการส่ง Student ID = 62005 ตรงๆ เข้า showStudent()

		assertEquals(expected.getId(), actual.getId());	//assertEquals(ซ้าย:expected,ขวา:actual)
		assertEquals(expected.getFName(), actual.getFName());
		assertEquals(expected.getLName(), actual.getLName());
		assertEquals(expected.getAge(), actual.getAge());
	}

    @Test
    void saveStudent() {
        StudentRequest expectedRequest = new StudentRequest(7L,62007, "Thanathon", "Juangroongruangkit", 40);
        Student expectedStudent = new Student(7L,62007, "Thanathon", "Juangroongruangkit", 40);

        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);
        //Student actual = studentService.saveStudent(expectedRequest);
        //actual = ค่าจริงที่ได้จากการเรียกใช้งาน service
/*
        assertEquals(expectedStudent.getId(), actual.getId());
        assertEquals(expectedStudent.getStdID(), actual.getStdID());
        assertEquals(expectedStudent.getFName(), actual.getFName());
        assertEquals(expectedStudent.getLName(), actual.getLName());
        assertEquals(expectedStudent.getAge(), actual.getAge());*/
    }

    /*@Test(expected = NullPointerException.class)
    void deleteStudent() {
        //String deleteMessage = new String("Delete student successfully");
        int studentId = 62004;
        Student expectedStudent = studentRepository.findBystdID(expected.getStdID());

        //when(studentRepository.deleteById(eq(expectedStudent.getId()))).thenThrow(NullPointerException.class);


        //assertEquals(null, studentService.deleteStudent(studentId));

    }*/

    /*
    deleteStudent
    updateStudentFirstName
    */

}

