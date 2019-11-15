package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.StudentRequest;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.demo.constant.StudentConstant.*;

@Service
public class StudentService extends Student{

    @Autowired
    StudentRepository studentRepository;

    public List <Collection<Student>> showAllStudents() throws BusinessException {
        //ดัก ไม่พบ student สักคน + server error
        Collection collection = studentRepository.findAll();
        List list = new ArrayList();
        list.addAll(collection);

        if(collection.isEmpty())
            throw new BusinessException(10003, ERROR_NOT_FOUND_ANY_STUDENT_IN_DB);

        return list;
    }

    public Student showStudent(Integer stdID) throws BusinessException {
        //ดัก หา student ID ไม่เจอ + server error
        Student thisStudent = studentRepository.findBystdID(stdID);
        if(thisStudent == null)
            throw new BusinessException(10002, ERROR_NOT_FOUND_STUDENT_IN_DB);
        return thisStudent;
    }

    public Student saveStudent(StudentRequest request) throws BusinessException {     //OK
        //ดัก student ID ซ้ำ + server error
        if (checkExistingStudentId(request))    //ถ้ามี Student นี้อยู่แล้ว(!=null) ได้ true มาให้ throw exception
            throw new BusinessException(10001, ERROR_EXISTING_STUDENT_IN_DB);

        Student s = new Student(request.getStdID(), request.getFName(), request.getLName(), request.getAge());
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

    public boolean checkExistingStudentId(StudentRequest request){        //OK
        Student student = studentRepository.findBystdID(request.getStdID());
        return student != null; //ถ้ามี Student นี้อยู่แล้ว(!=null)? true : false
    }
}
