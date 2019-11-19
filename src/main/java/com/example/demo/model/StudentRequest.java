package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data //Lombok : generate getter setter (***must install plugins "lombok")
@AllArgsConstructor //generate constructors with all of arguments (stdID,fName,lName,age)
public class StudentRequest {

    @Column(unique = true)
    private Long id;

    @NotNull
    @Pattern(regexp = "[0-9]{7}", message = "{Student ID must be 7 digits number.}")
    @Column(unique = true)
    private Integer stdID;

    @NotNull
    @Size(min=2, message = "{Last name length must more than 1 character}")
    @Size(max=40, message = "{Last name length must less than or equal 40 character}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{First name must be only characters}")
    private String fName;

    @NotNull
    @Size(min=2, message = "{Last name length must more than 1 character}")
    @Size(max=40, message = "{Last name length must less than or equal 40 characters}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{Last name must be only characters}")
    private String lName;

    @NotNull
    @Range(min=1, max=120, message = "{Age Must between 1-120 years old}")
    @Pattern(regexp = "[0-9]+", message = "{Age must be only numbers}")
    private Integer age;

    /*public StudentRequest(Long id, Integer stdID, String fName, String lName, Integer age){
        this.id = id;
        this.stdID = stdID;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }*/

    public StudentRequest(Integer stdID, String fName, String lName, Integer age){
        this.stdID = stdID;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }
}
