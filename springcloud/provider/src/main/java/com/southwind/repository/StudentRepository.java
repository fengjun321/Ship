package com.southwind.repository;
import com.southwind.entity.Student;
import java.util.Collection;

public interface StudentRepository {
    public Collection<Student> findAll();
    public Student findById(Integer id);
    public void saveOrUpdate(Student student);
    public void deleteById(Integer id);
}