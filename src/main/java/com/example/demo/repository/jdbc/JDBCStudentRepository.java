package com.example.demo.repository.jdbc;


import com.example.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCStudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Student findBystdID(Integer stdID) {
        Student result ;
        try{
            String sql = "SELECT * FROM student WHERE studentid = ?";
             result = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{stdID},
                    (rs, i) -> Student.builder()
                            .id(rs.getLong("id"))       //return object จากการ Query DB แบบไม่ใช้ RowMapper
                            .stdID(rs.getInt("studentid"))
                            .fName(rs.getString("firstname"))
                            .lName(rs.getString("lastname"))
                            .age(rs.getInt("age"))
                            .build());
            //ปกติ Student s = Student.builder().stdID(stdID).fName(fName).lName(lName).age(age).build();
            //rs.getXXX คือ get ค่าจาก DB ที่มี field ชื่อ "YYYY"
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
            return result;
    }

}
