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

    private String deleteMessage = new String();
    private String updateMessage = new String();

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

        try {
            Student s = studentRepository.findBystdID(stdID);
            studentRepository.deleteById(s.getId());
        } catch (NullPointerException e) {
            deleteMessage = notFoundStudentMessage;
        } catch (RuntimeException e) {
            deleteMessage = deleteSuccessMessage;
        }
        return deleteMessage;
    }

    public String updateStudentFirstName(Integer stdID, String fName) {
        //ดัก student ID ซ้ำ + server error
        String notFoundStudentMessage = new String("Cannot found student ID :" + stdID + "!");
        String updateSuccessMessage = new String("Update First name for student ID :" + stdID + " successfully!, New first name is : " + fName);

        try {
            Student s = studentRepository.findBystdID(stdID);
            s.setFName(fName);
            studentRepository.save(s);
            updateMessage = updateSuccessMessage;
        } catch (NullPointerException e) {
            updateMessage = notFoundStudentMessage;
        }
        return updateMessage;
    }

    /*public Boolean checkStudentId(Integer stdID){

        Student s = studentRepository.findBystdID(stdID);
        if (s.getId() != null) {
            return true;
        }
        return false;
    }*/
}
