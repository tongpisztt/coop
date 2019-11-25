package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.StudentRequest;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.jdbc.JDBCStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.constant.StudentConstant.*;

@Service
public class StudentService extends Student{

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JDBCStudentRepository jdbcStudentRepository;

    public List<Student> getAllStudents() throws BusinessException {
        //ดัก ไม่พบ student สักคน + server error
        List<Student> students = studentRepository.findAll();

        if(students.isEmpty())
            throw new BusinessException(10003, ERROR_NOT_FOUND_ANY_STUDENT_IN_DB);

        return students;
    }

    public Student getStudent(Integer stdID) throws BusinessException {
        //ดัก หา student ID ไม่เจอ + server error
        Student s = jdbcStudentRepository.findBystdID(stdID);
//        jdbcStudentRepository.findBystdID(stdID) return เป็นคลาส Student
        if(s == null)
            throw new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB);
        return s;

//        public Student getStudent(Integer stdID) throws BusinessException {
//            //ดัก หา student ID ไม่เจอ + server error
//            List s = (List<Student>)jdbcStudentRepository.findBystdID(stdID);
////        jdbcStudentRepository.findBystdID(stdID) return เป็นคลาส Student
//            if (s.size() == 0)
//                throw new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB);
//            return jdbcStudentRepository.findBystdID(stdID);
//        }
    }

    public Student addStudent(StudentRequest request) throws BusinessException {     //OK
        //ดัก student ID ซ้ำ + server error
        if (checkExistingStudentId(request))    //ถ้ามี Student นี้อยู่แล้ว(!=null) ได้ true มาให้ throw exception
            throw new BusinessException(10001, ERROR_EXISTING_STUDENT_IN_DB);

        Student s = Student.builder()
                .stdID(request.getStdID())
                .fName(request.getFName())
                .lName(request.getLName())
                .age(request.getAge())
                .build();

        //Student s = new Student(request.getStdID(), request.getFName(), request.getLName(), request.getAge());

        return studentRepository.save(s);
    }

    public String deleteStudent(Integer stdID) throws BusinessException {       //OK
        //ดัก student ID ซ้ำ + server error
        Student thisStudent = studentRepository.findBystdID(stdID);
        if(thisStudent == null)
            throw new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB);

        studentRepository.deleteById(thisStudent.getId());
        return DELETE_SUCCESSFULLY ;
    }

    public Student updateStudentFirstName(Integer stdID, String fName) throws BusinessException {
        //ดัก student ID ซ้ำ + server error + ไม่พบ student
        //String updateSuccessMessage = String.format("%s, Student ID : %d has been change first name to %s", UPDATE_FIRST_NAME_SUCCESSFULLY, stdID, fName) ;
        Student thisStudent = studentRepository.findBystdID(stdID);
        if (thisStudent == null)
            throw new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB);

        thisStudent.setFName(fName);
        return studentRepository.save(thisStudent);
    }

    private boolean checkExistingStudentId(StudentRequest request){        //OK
        Student student = studentRepository.findBystdID(request.getStdID());
        return student != null && student.getStdID().equals(request.getStdID());
        //ถ้ามี Student นี้อยู่แล้ว ได้ true && ถ้า student.getStdID = request.getStdID() จะได้ true => คืน true แปลว่า Existing
    }
}
