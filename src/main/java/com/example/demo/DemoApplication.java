package com.example.demo;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	ApplicationRunner init(StudentRepository studentRepository) {
		return args -> {
			Student s1 = new Student(62001,"AAAA","BBBB",15);
			Student s2 = new Student(62002,"CCCC","DDDD",16);
			studentRepository.save(s1);
			studentRepository.save(s2);

		};
	}
}
