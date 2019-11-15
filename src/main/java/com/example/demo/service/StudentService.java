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
import java.util.stream.Collectors;

import static com.example.demo.constant.StudentConstant.ERROR_SAVE_DB;

//@Component
@Service
public class StudentService extends Student{

    @Autowired
    StudentRepository studentRepository;

    private String saveMessage = new String();
    private String deleteMessage = new String();
    private String updateMessage = new String();

    public List <Collection<Student>> showAllStudents() {
        //ดัก ไม่พบ student สักคน + server error
        List<Collection<Student>> list = new ArrayList<Collection<Student>>();
        list.add(studentRepository.findAll().stream().collect(Collectors.toList()));
        return list;
    }

    public Student showStudent(Integer stdID) throws BusinessException {
        //ดัก หา student ID ไม่เจอ + server error
        //StudentService service = new StudentService();
        Student thisStudent = studentRepository.findBystdID(stdID);

            //service.findStudentInDatabase(thisStudent);
        if(thisStudent == null)
            throw new BusinessException(ERROR_SAVE_DB);
        else
            return thisStudent;
    }

    public Student saveStudent(StudentRequest request) throws BusinessException {     //OK
        String repeatStudentMessage = String.format("This student ID : %s have been already save in database!", request.getStdID());

        String saveSuccessMessage = new String("Save student ID :" + request.getStdID() + " successfully!");
        //ดัก student ID ซ้ำ + server error
        if (checkExistingStudentId(request)) {
            throw new BusinessException(10001, "PK is existing.");
        }

        Student s = new Student(request.getStdID(), request.getFName(), request.getLName(), request.getAge());
        return studentRepository.save(s);

//        int check = 0;
//            check = checkRepeatStudentId(request);
//            List<Student> originalStudentList = studentRepository.findAll();
//            if (check == 0) {
//                Student s = new Student(request.getStdID(), request.getFName(), request.getLName(), request.getAge());
//                studentRepository.save(s);
//                saveMessage = saveSuccessMessage;
//            } else {
//                saveMessage = repeatStudentMessage;
//            }
//        return saveMessage;
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
        //ดัก student ID ซ้ำ + server error + ไม่พบ student
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

    public boolean checkExistingStudentId(StudentRequest request){        //OK
        int counter = 0;
        Student student = studentRepository.findBystdID(request.getStdID());
//        System.out.println("This is number of students : " + originalStudentList.size());
//
//        for (int i = 0; i < originalStudentList.size(); i++) {
//            System.out.println(request.getStdID() +":"+ originalStudentList.get(i).getStdID());
//            if (request.getStdID().equals(originalStudentList.get(i).getStdID()))
//                counter++;
//        }
//        System.out.println("now counter is : " + counter);
        return student != null;
    }
}
