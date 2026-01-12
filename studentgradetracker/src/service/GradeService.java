package service;

import dao.StudentDAO;
import model.Student;
import util.GradeCalculator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GradeService {

    private final StudentDAO studentDAO;

    public GradeService(Connection connection) {
        this.studentDAO = new StudentDAO(connection);
    }

   
    public void addStudent(Student student) throws SQLException {

        // Business validation
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (student.getSubjectMarks() == null || student.getSubjectMarks().isEmpty()) {
            throw new IllegalArgumentException("Student must have at least one subject mark");
        }

        
        GradeCalculator.calculateStudentResult(student);

        
        studentDAO.saveStudent(student);
    }

   
    public Student getStudentById(int studentId) throws SQLException {

        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        return studentDAO.getStudentById(studentId);
    }

    
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    
    public void deleteStudent(int studentId) throws SQLException {

        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        studentDAO.deleteStudent(studentId);
    }
}

