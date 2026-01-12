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

    /**
     * Creates a student, calculates results, and saves to database.
     */
    public void addStudent(Student student) throws SQLException {

        // Business validation
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (student.getSubjectMarks() == null || student.getSubjectMarks().isEmpty()) {
            throw new IllegalArgumentException("Student must have at least one subject mark");
        }

        // Calculate total, average, grade
        GradeCalculator.calculateStudentResult(student);

        // Persist using DAO
        studentDAO.saveStudent(student);
    }

    /**
     * Fetch a student with subject marks by ID.
     */
    public Student getStudentById(int studentId) throws SQLException {

        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        return studentDAO.getStudentById(studentId);
    }

    /**
     * Fetch all students (basic details).
     */
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    /**
     * Delete a student by ID.
     */
    public void deleteStudent(int studentId) throws SQLException {

        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        studentDAO.deleteStudent(studentId);
    }
}

