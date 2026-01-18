package com.example.demo.student;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StudentDTOMapper implements Function<Student, StudentDTOResponse> {

    @Override
    public StudentDTOResponse apply(Student student){
        return new StudentDTOResponse(student.getEmail(), student.getName(), student.getAge());
    }
}
