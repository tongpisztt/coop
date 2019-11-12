package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.model.StudentRequest;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//@Component
@Service
public class StudentService extends Student{

    @Autowired
    StudentRepository studentRepository;

    private String message = new String();

    public List <Collection<Student>> showAllStudents() {
        //ดัก ไม่พบ student สักคน + server error
        List<Collection<Student>> list = new ArrayList<Collection<Student>>();
        list.add(studentRepository.findAll().stream().collect(Collectors.toList()));
        return list;
    }

    /*public Student showStudent(Integer stdID) {
        try{
            if (checkStudentIdNotFound(stdID)){

            }
            System.out.println(message);
        }catch (NullPointerException e){
            System.out.println("Cannot found student ID");
            throw e; // rethrowing the exception
        }
        Student find = studentRepository.findBystdID(stdID);
        return find;
    }*/

    public Student showStudent(Integer stdID) {
        //ดัก หา student ID ไม่เจอ + server error
        Student find = studentRepository.findBystdID(stdID);
        return find;
    }

    public Student saveStudent(StudentRequest request) {
        //ดัก student ID ซ้ำ + server error
        Student s = new Student(request.getId(), request.getStdID(),request.getFName(),request.getLName(),request.getAge());
        studentRepository.save(s);
        return s;
    }

    public String deleteStudent(Integer stdID) {       //OK
        //ดัก student ID ซ้ำ + server error

        String notFoundStudentMessage = new String("Cannot found student ID :" + stdID + "!");
        String deleteSuccessMessage = new String("Delete student ID :" + stdID + " successfully!");
        Student s = studentRepository.findBystdID(stdID);
        try {
            studentRepository.deleteById(s.getId());
        } catch (NullPointerException e) {
            message = notFoundStudentMessage;
        } catch (RuntimeException e) {
            message = deleteSuccessMessage;
        }
        return message;
    }

    public void updateStudentFirstName(Integer stdID, String fName) {
        //ดัก student ID ซ้ำ + server error
        Student s = studentRepository.findBystdID(stdID);
        s.setFName(fName);
        studentRepository.save(s);
    }

    public Boolean checkStudentId(Integer stdID){

        Student s = studentRepository.findBystdID(stdID);
        if (s.getId() != null) {
            return true;
        }
        return false;
    }
}
