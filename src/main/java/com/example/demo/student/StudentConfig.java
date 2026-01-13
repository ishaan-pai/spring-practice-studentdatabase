package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepo repo){
        return args -> {
            Student mariam = new Student("mariam.jamal@gmail.com", "Mariam", LocalDate.of(2000, Month.JANUARY, 1));
            Student alex = new Student("alex.jamal@gmail.com", "Alex", LocalDate.of(2004, Month.JANUARY, 1));

            repo.saveAll(List.of(mariam, alex));
        };
    }
}
