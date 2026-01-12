CREATE DATABASE IF NOT EXISTS studentgradetracker;
USE studentgradetracker;


CREATE TABLE IF NOT EXISTS student (
    student_id     INT PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    total_marks    INT NOT NULL,
    average_marks  DECIMAL(5,2) NOT NULL,
    grade          CHAR(1) NOT NULL
);


CREATE TABLE IF NOT EXISTS student_subject_marks (
    mark_id    INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    subject_no INT NOT NULL,
    marks      INT NOT NULL,

    CONSTRAINT fk_student_marks
        FOREIGN KEY (student_id)
        REFERENCES student(student_id)
        ON DELETE CASCADE
);
