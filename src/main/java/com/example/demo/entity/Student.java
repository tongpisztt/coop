package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data       //getter setter
@Builder    //ใช้กับ Model
@Table(name = "Student", schema = "testdb")
public class Student {

    @Id
    //@SequenceGenerator(name = "id_seq", sequenceName = "id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_seq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "StudentID")
    private Integer stdID;

    @Column(name = "Firstname")
    private String fName;

    @Column(name = "Lastname")
    private String lName;

    @Column(name = "Age")
    private Integer age;

    public Student(){
    }

    public Student(Long id, Integer stdID, String fName, String lName, Integer age){
        this.id = id;
        this.stdID = stdID;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }

    public Student(Integer stdID, String fName, String lName, Integer age){
        this.stdID = stdID;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }


}