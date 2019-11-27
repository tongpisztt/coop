package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.StudentRequest;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.constant.StudentConstant.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = StudentController.class)
//พื่อบอกว่านี่คือ unit test สำหรับ StudentController และเพื่อให้เราสามารถใช้ @MockBean ในการ mock StudentService ซึ่งเป็น dependency พร้อมทำการ injected ให้กับ StudentController
public class StudentControllerTest {        //เเทส API ...ถ้าเคสถูก ได้objectตามที่เราเขียนไว้ไหม, ถ้าผิด ได้ status ตามที่เขียนไว้ไหม

    @Mock
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController studentController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    //---GoodCase---------------------------------------------------------------------------------------------------------------
    @Test   //แบบที่มองว่า controller เป็น classๆ นึง
    public void testGetAllStudentsSuccessfully() throws Exception {

        //Arrange
        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);
        List studentListInDB = Arrays.asList(s1, s2);
        when(studentService.getAllStudents()).thenReturn(studentListInDB);

        ResponseEntity result = studentController.getAllStudents();
        List<Student> students = (List<Student>) result.getBody();
// Test set ที่ถูกต้องของ students : index 0
//        Assert.assertEquals(Long.valueOf(5),students.get(0).getId());     //ไว้เทส ว่าถ้าลงใส่ค่าผิด ก็จะ error
//        Assert.assertEquals(Integer.valueOf(62005),students.get(0).getStdID());  //students.get(indexที่เท่าไร)
//        Assert.assertEquals("Prayuth",students.get(0).getFName());
//        Assert.assertEquals("Jundara",students.get(0).getLName());
//        Assert.assertEquals(Integer.valueOf(100),students.get(0).getAge());
// Test set ผิดของ students : index 0
//        Assert.assertEquals(Long.valueOf(1),students.get(0).getId());     //ไว้เทส ว่าถ้าลงใส่ค่าผิด ก็จะ error
//        Assert.assertEquals(Integer.valueOf(62001),students.get(0).getStdID());  //students.get(indexที่เท่าไร)
//        Assert.assertEquals("AAAA",students.get(0).getFName());
//        Assert.assertEquals("BBBB",students.get(0).getLName());
//        Assert.assertEquals(Integer.valueOf(10),students.get(0).getAge());

        Assert.assertEquals(s1.getId(), students.get(0).getId());
        Assert.assertEquals(s1.getStdID(), students.get(0).getStdID());
        Assert.assertEquals(s1.getFName(), students.get(0).getFName());
        Assert.assertEquals(s1.getLName(), students.get(0).getLName());
        Assert.assertEquals(s1.getAge(), students.get(0).getAge());

        Assert.assertEquals(s2.getId(), students.get(1).getId());
        Assert.assertEquals(s2.getStdID(), students.get(1).getStdID());
        Assert.assertEquals(s2.getFName(), students.get(1).getFName());
        Assert.assertEquals(s2.getLName(), students.get(1).getLName());
        Assert.assertEquals(s2.getAge(), students.get(1).getAge());

    }

    @Test
    public void testGetStudentSuccessfully() throws Exception {

        //Arrange
        Student s = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        when(studentService.getStudent(62005)).thenReturn(s);

        ResponseEntity result = studentController.getStudent(62005);
        Student students = (Student) result.getBody();

        Assert.assertEquals(s.getId(), students.getId());
        Assert.assertEquals(s.getStdID(), students.getStdID());
        Assert.assertEquals(s.getFName(), students.getFName());
        Assert.assertEquals(s.getLName(), students.getLName());
        Assert.assertEquals(s.getAge(), students.getAge());
    }

    @Test
    public void testAddStudentSuccessfully() throws Exception {

        //Arrange
        StudentRequest studentRequest = new StudentRequest(null, 62005, "XXXXX", "Jundara", 100);
        Student studentInDB = new Student(5L, 62005, "XXXXX", "Jundara", 100);
//        when(studentService.addStudent(eq(studentRequest))).thenReturn(studentInDB);

        ResponseEntity result = studentController.addStudent(studentRequest);
        Student student = (Student) result.getBody();

//        Assert.assertEquals(Integer.valueOf(62001), student.getStdID());   //เอาไว้เทสเคสผิด เพราะมัน expected:62001 แต่ได้ค่าจริงคือ :62005
        Assert.assertEquals(studentRequest.getStdID(), student.getStdID());
        Assert.assertEquals(studentRequest.getFName(), student.getFName());

    }

    @Test
    public void testDeleteStudentSuccessfully() throws Exception {

        //Arrange
        Student s = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        when(studentService.deleteStudent(62005)).thenReturn(DELETE_SUCCESSFULLY);

        ResponseEntity result = studentController.deleteStudent(62005);
        String str = (String) result.getBody();

        Assert.assertEquals(DELETE_SUCCESSFULLY, str);
        //Assert.assertEquals(ERROR_EXISTING_STUDENT_IN_DB, str);   //เอาไว้เทส ลองใส่ string ที่ผิดไป จะขึ้น error
    }

    @Test
    public void testUpdateStudentFirstNameSuccessfully() throws Exception {

        //Arrange
        String newFName = "XXXXX";
        Student studentAfterUpdate = new Student(5L, 62005, "XXXXX", "Jundara", 100);
//        when(studentService.updateStudentFirstName(62005, newFName)).thenReturn(studentAfterUpdate);

        ResponseEntity result = studentController.updateStudentFirstName(62005, newFName);
        Student student = (Student) result.getBody();

//        Assert.assertEquals("ZZZZZ", student.getFName());   //เอาไว้เทสเคสผิด เพราะมัน expected:<[ZZZZZ]> แต่ได้ :<[XXXXX]>
        Assert.assertEquals(studentAfterUpdate.getId(), student.getId());
        Assert.assertEquals(newFName, student.getFName());

    }

//    @Test //แบบ path
//    public void testGetAllStudentsSuccessfully() throws Exception {
//
//        //Arrange
//        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
//        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);
//        List studentListInDB = Arrays.asList(s1, s2);
//        when(studentService.showAllStudents()).thenReturn(studentListInDB);
//
//        mockMvc.perform(get("/students/all").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id").value(s1.getId()))
//                .andExpect(jsonPath("$[0].stdID").value(s1.getStdID()))
//                .andExpect(jsonPath("$[0].fname").value(s1.getFName()))
//                .andExpect(jsonPath("$[0].lname").value(s1.getLName()))
//                .andExpect(jsonPath("$[0].age").value(s1.getAge()))
//                .andExpect(jsonPath("$[1].id").value(s2.getId()))
//                .andExpect(jsonPath("$[1].stdID").value(s2.getStdID()))
//                .andExpect(jsonPath("$[1].fname").value(s2.getFName()))
//                .andExpect(jsonPath("$[1].lname").value(s2.getLName()))
//                .andExpect(jsonPath("$[1].age").value(s2.getAge()))
//                .andDo(print());
//
//        ResponseEntity result = studentController.getAllStudents();
//        List<Student> students = (List<Student>) result.getBody();
//        Assert.assertEquals(s1.getId(),students.get(0).getId());
//
//    }
/*
    @Test
    public void testGetStudentSuccessfully() throws Exception {

        //Arrange
        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);
        when(studentService.showStudent(62005)).thenReturn(s1);
        when(studentService.showStudent(62006)).thenReturn(s2);

        mockMvc.perform(get("/students/getStudent/62005").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(s1.getId()))
                .andExpect(jsonPath("$.stdID").value(s1.getStdID()))
                .andExpect(jsonPath("$.fname").value(s1.getFName()))
                .andExpect(jsonPath("$.lname").value(s1.getLName()))
                .andExpect(jsonPath("$.age").value(s1.getAge()))
                .andDo(print());
    }

    @Test
    public void testAddStudentSuccessfully() throws Exception {

        String postURL = BASE_URL + "/add";

        StudentRequest rq1 = new StudentRequest(62005, "Prayuth", "Jundara", 100);
        StudentRequest rq2 = new StudentRequest(62006, "Prawit", "Wongkumlao", 90);

        //Arrange
        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);
        //when(studentService.saveStudent(rq1)).thenReturn(s1);
        //when(studentService.saveStudent(rq2)).thenReturn(s2);

        mockMvc.perform(post("/students/add").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(rq1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(s1.getId()))
                .andExpect(jsonPath("$.stdID").value(s1.getStdID()))
                .andExpect(jsonPath("$.fname").value(s1.getFName()))
                .andExpect(jsonPath("$.lname").value(s1.getLName()))
                .andExpect(jsonPath("$.age").value(s1.getAge()))
                .andDo(print());
    }*/
    //---WorstCase-CaseNotFoundStudent---------------------------------------------------------------------------------------------------------------

    @Test //(expected = BusinessException.class)
    public void testGetAllStudentsCaseNotFoundAnyStudent() throws BusinessException {

        when(studentService.getAllStudents()).thenThrow(new BusinessException(10003, ERROR_NOT_FOUND_ANY_STUDENT_IN_DB));
        //studentController.getAllStudents();

        ResponseEntity result = studentController.getAllStudents();
        String error = (String) result.getBody();

        Assert.assertEquals(ERROR_NOT_FOUND_ANY_STUDENT_IN_DB, error);
//        Assert.assertEquals("ERROR_NOT_FOUND_ANY_STUDENT_IN_DB", error); //ลองใส่ที่ผิดไป จะ error
    }

    @Test //(expected = BusinessException.class)
    public void testGetStudentCaseNotFoundStudent() throws BusinessException {

        int requestStdID = 62001;
        //Student s = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        when(studentService.getStudent(requestStdID)).thenThrow(new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB));
        //studentController.getStudent(requestStdID);

        ResponseEntity result = studentController.getStudent(requestStdID);
        String error = (String) result.getBody();

        Assert.assertEquals(ERROR_NOT_FOUND_STUDENT_IN_DB, error);
//        Assert.assertEquals("ERROR_NOT_FOUND_ANY_STUDENT_IN_DB", error); //ลองใส่ที่ผิดไป จะ error
    }

    @Test //(expected = BusinessException.class)
    public void testDeleteStudentCaseNotFoundStudent() throws BusinessException {

        int requestStdID = 62001;
        when(studentService.deleteStudent(requestStdID)).thenThrow(new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB));

        ResponseEntity result = studentController.deleteStudent(requestStdID);
        String error = (String) result.getBody();

        Assert.assertEquals(ERROR_NOT_FOUND_STUDENT_IN_DB, error);
//        Assert.assertEquals("ERROR_NOT_FOUND_ANY_STUDENT_IN_DB", error); //ลองใส่ที่ผิดไป จะ error
    }

    @Test //(expected = BusinessException.class)
    public void testUpdateStudentFirstNameCaseNotFoundStudent() throws BusinessException {

        int requestStdID = 62001;
        String newFname = "XXXXX";
        when(studentService.updateStudentFirstName(anyInt(), anyString())).thenThrow(new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB));

        ResponseEntity result = studentController.updateStudentFirstName(requestStdID, newFname);
        String error = (String) result.getBody();

        Assert.assertEquals(ERROR_NOT_FOUND_STUDENT_IN_DB, error);
//        Assert.assertEquals("ERROR_NOT_FOUND_ANY_STUDENT_IN_DB", error); //ลองใส่ที่ผิดไป จะ error
    }

    //---WorstCase-CaseExisting----------------------------------------------------------------------------------------------------------------------
    @Test //(expected = BusinessException.class)
    public void testAddStudentCaseExisting() throws BusinessException {

        StudentRequest r = new StudentRequest(null, 62005, "AAAAA", "BBBB", 40);
        //Student s = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        when(studentService.addStudent(r)).thenThrow(new BusinessException(10001, ERROR_EXISTING_STUDENT_IN_DB));
        //studentController.getStudent(requestStdID);

        ResponseEntity result = studentController.addStudent(r);
        String error = (String) result.getBody();

        Assert.assertEquals(ERROR_EXISTING_STUDENT_IN_DB, error);
//        Assert.assertEquals("ERROR_NOT_FOUND_ANY_STUDENT_IN_DB", error); //ลองใส่ที่ผิดไป จะ error
    }

    //---WorstCase-CaseSystemError----------------------------------------------------------------------------------------------------------------------
    @Test //(expected = BusinessException.class)
    public void testGetAllStudentsCaseSystemError() throws BusinessException {

        when(studentService.getAllStudents()).thenThrow(BusinessException.class);

        ResponseEntity result = studentController.getAllStudents();
        HttpStatus status = result.getStatusCode();

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);
//        Assert.assertEquals(HttpStatus.OK, status);
    }

    @Test //(expected = BusinessException.class)
    public void testGetStudentCaseSystemError() throws BusinessException {

        when(studentService.getStudent(anyInt())).thenThrow(BusinessException.class);

        ResponseEntity result = studentController.getStudent(62001);
        HttpStatus status = result.getStatusCode();

        Assert.assertEquals(HttpStatus.BAD_GATEWAY, status);
//        Assert.assertEquals(HttpStatus.OK, status);
    }

    @Test //(expected = BusinessException.class)
    public void testAddStudentCaseSystemError() throws BusinessException {

        StudentRequest r = new StudentRequest(null, 62005, "AAAAA", "BBBB", 40);
        when(studentService.addStudent(any(StudentRequest.class))).thenThrow(BusinessException.class);

        ResponseEntity result = studentController.addStudent(r);
        HttpStatus status = result.getStatusCode();

        Assert.assertEquals(HttpStatus.BAD_GATEWAY, status);
//        Assert.assertEquals(HttpStatus.OK, status);
    }

    @Test //(expected = BusinessException.class)
    public void testDeleteStudentCaseSystemError() throws BusinessException {
        int requestStdID = 62001;
        when(studentService.deleteStudent(anyInt())).thenThrow(BusinessException.class);

        ResponseEntity result = studentController.deleteStudent(requestStdID);
        HttpStatus status = result.getStatusCode();

        Assert.assertEquals(HttpStatus.BAD_GATEWAY, status);
//        Assert.assertEquals(HttpStatus.OK, status);
    }

    @Test //(expected = BusinessException.class)
    public void testUpdateStudentFirstNameCaseSystemError() throws BusinessException {

        int requestStdID = 62001;
        String newFname = "XXXXX";
        when(studentService.updateStudentFirstName(anyInt(),anyString())).thenThrow(BusinessException.class);

        ResponseEntity result = studentController.updateStudentFirstName(requestStdID, newFname);
        HttpStatus status = result.getStatusCode();

        Assert.assertEquals(HttpStatus.BAD_GATEWAY, status);
//        Assert.assertEquals(HttpStatus.OK, status);
    }
}



