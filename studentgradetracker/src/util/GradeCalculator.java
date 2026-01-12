package util;

import model.Student;

import java.util.List;

public final class GradeCalculator {

    // Prevent instantiation
    private GradeCalculator() {
    }

    /**
     * Calculates total marks from dynamic subject marks.
     */
    public static int calculateTotalMarks(List<Integer> subjectMarks) {
        if (subjectMarks == null || subjectMarks.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (int mark : subjectMarks) {
            total += mark;
        }
        return total;
    }

    /**
     * Calculates average marks.
     */
    public static double calculateAverageMarks(int totalMarks, int subjectCount) {
        if (subjectCount <= 0) {
            return 0.0;
        }
        return (double) totalMarks / subjectCount;
    }

    /**
     * Determines grade based on average marks.
     */
    public static char calculateGrade(double averageMarks) {

        if (averageMarks >= 90) {
            return 'A';
        } else if (averageMarks >= 75) {
            return 'B';
        } else if (averageMarks >= 60) {
            return 'C';
        } else if (averageMarks >= 40) {
            return 'D';
        } else {
            return 'F';
        }
    }

    /**
     * Calculates total, average, and grade for a student.
     * This method is intended to be called from the Service layer
     * BEFORE persisting the student using DAO.
     */
    public static void calculateStudentResult(Student student) {

        if (student == null || student.getSubjectMarks() == null) {
            return;
        }

        List<Integer> marks = student.getSubjectMarks();

        int totalMarks = calculateTotalMarks(marks);
        double averageMarks = calculateAverageMarks(totalMarks, marks.size());
        char grade = calculateGrade(averageMarks);

        student.setTotalMarks(totalMarks);
        student.setAverageMarks(averageMarks);
        student.setGrade(grade);
    }
}
