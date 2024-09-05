package com.example.jpastudent.controller;

import com.example.jpastudent.model.Student;
import com.example.jpastudent.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentRestController {
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> students() {
         return studentRepository.findAll();
    }

    @GetMapping("/addstudent")
    public List<Student> addStudent() {
        Student std = new Student();
        std.setBornDate(LocalDate.now());
        std.setBornTime(LocalTime.now());
        std.setName("JOHN");
        studentRepository.save(std);
        return studentRepository.findAll();
    }

    @GetMapping("/students/{name}")
    public List<Student> getallstudentsbyname(@PathVariable String name){
        return studentRepository.findAllByName(name);
    }

    @GetMapping("/students/{bornDate}")
    public List<Student> getallstudentsbyborndate(@PathVariable LocalDate bornDate){
        return studentRepository.findAllByBornDate(bornDate);
    }

    @GetMapping("/students/{id}")
    public List<Student> getallstudentsbyborndate(@PathVariable int id){
        studentRepository.deleteStudentById(id);
        return studentRepository.findAll();
    }

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Student postStudent(@RequestBody Student student) {
        System.out.println(student);
        return studentRepository.save(student);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> orgStudent = studentRepository.findById(id);
        if (orgStudent.isPresent()) {
            studentRepository.save(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        Optional<Student> orgStudent = studentRepository.findById(id);
        if (orgStudent.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.ok("STUDENT DELETED");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("STUDENT NOT FOUND");
        }
    }

}
