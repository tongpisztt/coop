package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@RunWith(JUnit4.class)
@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
@WebMvcTest(controllers = StudentController.class)
//พื่อบอกว่านี่คือ unit test สำหรับ StudentController และเพื่อให้เราสามารถใช้ @MockBean ในการ mock StudentService ซึ่งเป็น dependency พร้อมทำการ injected ให้กับ StudentController
public class StudentControllerTest {        //เเทส API ...ถ้าเคสถูก ได้objectตามที่เราเขียนไว้ไหม, ถ้าผิด ได้ status ตามที่เขียนไว้ไหม

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    /*@InjectMocks
    private StudentController studentController;*/

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    //---GoodCase---------------------------------------------------------------------------------------------------------------

    /*@Test
    public void testGetAllStudentsSuccessfully() throws Exception {
        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);
        List studentListInDB = Arrays.asList(s1, s2);
        when(studentService.showAllStudents()).thenReturn(studentListInDB);

        studentController.getAllStudents();

    }*/
    @Test
    public void testGetAllStudentsSuccessfully() throws Exception {

        //Arrange
        Student s1 = new Student(5L, 62005, "Prayuth", "Jundara", 100);
        Student s2 = new Student(6L, 62006, "Prawit", "Wongkumlao", 90);
        List studentListInDB = Arrays.asList(s1, s2);
        when(studentService.showAllStudents()).thenReturn(studentListInDB);

        mockMvc.perform(get("/students/all").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(s1.getId()))
                .andExpect(jsonPath("$[0].stdID").value(s1.getStdID()))
                .andExpect(jsonPath("$[0].fname").value(s1.getFName()))
                .andExpect(jsonPath("$[0].lname").value(s1.getLName()))
                .andExpect(jsonPath("$[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$[1].id").value(s2.getId()))
                .andExpect(jsonPath("$[1].stdID").value(s2.getStdID()))
                .andExpect(jsonPath("$[1].fname").value(s2.getFName()))
                .andExpect(jsonPath("$[1].lname").value(s2.getLName()))
                .andExpect(jsonPath("$[1].age").value(s2.getAge()))
                .andDo(print());
    }
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
    //---WorstCase----------------------------------------------------------------------------------------------------------------
}
