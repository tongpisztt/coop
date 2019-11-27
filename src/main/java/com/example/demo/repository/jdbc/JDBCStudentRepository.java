package com.example.demo.repository.jdbc;

import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCStudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Student s) {

        try {
            String insertQuery = "INSERT INTO student (studentid, firstname, lastname, age) VALUES(?,?,?,?)";
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(insertQuery);
                ps.setInt(1, s.getStdID());
                ps.setString(2, s.getFName());
                ps.setString(3, s.getLName());
                ps.setInt(4, s.getAge());
                return ps;
            });

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateFirstName(Integer stdID, String fName) {

        try {
            String updateFirstNameQuery = "UPDATE student SET firstname = ? WHERE studentid = ?";
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(updateFirstNameQuery);
                ps.setString(1, fName);
                ps.setInt(2, stdID);
                return ps;
            });

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Student findBystdID(Integer stdID) {
        Student result;
        try {
            String findBystdIDQuery = "SELECT * FROM student WHERE studentid = ?";
            result = jdbcTemplate.queryForObject(
                    findBystdIDQuery,
                    new Object[]{stdID},       //obj ที่จะใส่แทน ? ตำแหน่งที่ 1
                    (rs, i) -> Student.builder()
                            .id(rs.getLong("id"))       //return object จากการ Query DB แบบไม่ใช้ RowMapper
                            .stdID(rs.getInt("studentid"))
                            .fName(rs.getString("firstname"))
                            .lName(rs.getString("lastname"))
                            .age(rs.getInt("age"))
                            .build());
            //ปกติ Student s = Student.builder().stdID(stdID).fName(fName).lName(lName).age(age).build();
            //rs.getXXX คือ get ค่าจาก DB ที่มี field ชื่อ "YYYY"
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        return result;
    }

    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        try {
            String findAllQuery = "SELECT * FROM student";
            list = jdbcTemplate.query(
                    findAllQuery, (rs, i) -> Student.builder()
                            .id(rs.getLong("id"))       //return object จากการ Query DB แบบไม่ใช้ RowMapper
                            .stdID(rs.getInt("studentid"))
                            .fName(rs.getString("firstname"))
                            .lName(rs.getString("lastname"))
                            .age(rs.getInt("age"))
                            .build());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public void deleteById(Long id) {
        try {
            String deleteQuery = "DELETE FROM student WHERE id = ?";
            jdbcTemplate.update(deleteQuery, id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
