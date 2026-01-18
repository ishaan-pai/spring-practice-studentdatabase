package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepo studentRepo;
    private final StudentDTOMapper studentDTOMapper;

    @Autowired
    public StudentService(StudentRepo studentRepo, StudentDTOMapper studentDTOMapper) {
        this.studentRepo = studentRepo;
        this.studentDTOMapper = studentDTOMapper;
    }

    public List<StudentDTOResponse> getStudents() {
        return studentRepo.findAll().stream().map(studentDTOMapper).collect(Collectors.toList());
    }

    public Optional<StudentDTOResponse> getStudent(Long studentId){
        Optional<Student> studentOptional = studentRepo.findStudentById(studentId);

        if (studentOptional.isEmpty()){
            throw new IllegalStateException("Student does not exist");
        }

        return studentRepo.findStudentById(studentId).map(studentDTOMapper);
    }

    public Student addNewStudent(Student student) {
        Optional<StudentDTOResponse> studentOptional = studentRepo.findStudentByEmail(student.getEmail()).map(studentDTOMapper);

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email Taken");
        }

        return student;
    }

    public void deleteStudent(Long studentId){
        boolean exists = studentRepo.existsById(studentId);

        if (!exists){
            throw new IllegalStateException("Student with id " + studentId + " does not exist");
        }

        studentRepo.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId,
                              String name,
                              String email){

        Student student = studentRepo.findById(studentId).orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exist."));

        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)){

            Optional<Student> studentOptional = studentRepo.findStudentByEmail(email);

            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }

    }
}
