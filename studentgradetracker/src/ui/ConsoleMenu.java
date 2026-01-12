package ui;


import model.Student;
import service.GradeService;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import config.DBConnection;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final GradeService gradeService;

    public ConsoleMenu() throws SQLException {
        Connection connection = DBConnection.getConnection();
        this.gradeService = new GradeService(connection);
    }

    public void start() {

        while (true) {
            System.out.println("\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Student By ID");
            System.out.println("3. View All Students");
            System.out.println("4. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            int choice = readInt();

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudentById();
                    case 3 -> viewAllStudents();
                    case 4 -> deleteStudent();
                    case 0 -> {
                        System.out.println("\n=====EXITING APPLICATION...=====");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addStudent() throws SQLException {

        System.out.print("Enter Student ID: ");
        int studentId = readInt();

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        Student student = new Student(studentId, name);

        System.out.print("Enter number of subjects: ");
        int subjectCount = readInt();

        if (subjectCount <= 0) {
            System.out.println("Subject count must be greater than zero.");
            return;
        }

        for (int i = 1; i <= subjectCount; i++) {
            System.out.print("Enter marks for Subject " + i + ": ");
            int marks = readInt();
            student.addSubjectMark(marks);
        }

        gradeService.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private void viewStudentById() throws SQLException {

        System.out.print("Enter Student ID: ");
        int studentId = readInt();

        Student student = gradeService.getStudentById(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("\n--- Student Details ---");
        System.out.println(student);
        System.out.println("Subject Marks: " + student.getSubjectMarks());
    }

    private void viewAllStudents() throws SQLException {

        List<Student> students = gradeService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n--- All Students ---");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void deleteStudent() throws SQLException {

        System.out.print("Enter Student ID to delete: ");
        int studentId = readInt();

        gradeService.deleteStudent(studentId);
        System.out.println("Student deleted successfully.");
    }

    
    private int readInt() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter again: ");
            }
        }
    }
}

