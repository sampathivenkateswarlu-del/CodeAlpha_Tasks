package model;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private int studentId;
    private String name;

   
    private List<Integer> subjectMarks;

    private int totalMarks;
    private double averageMarks;
    private char grade;

    public Student() {
        this.subjectMarks = new ArrayList<>();
    }

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.subjectMarks = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getSubjectMarks() {
        return subjectMarks;
    }

    public void setSubjectMarks(List<Integer> subjectMarks) {
        this.subjectMarks = subjectMarks;
    }

    public void addSubjectMark(int mark) {
        this.subjectMarks.add(mark);
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(double averageMarks) {
        this.averageMarks = averageMarks;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student {" +
               "ID=" + studentId +
               ", Name='" + name + '\'' +
               ", Subjects=" + subjectMarks.size() +
               ", TotalMarks=" + totalMarks +
               ", Average=" + averageMarks +
               ", Grade=" + grade +
               '}';
    }
}
