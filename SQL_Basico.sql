CREATE TABLE courses (
    course_id INT IDENTITY(1,1) CONSTRAINT PK_courses PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    duration_weeks INT NOT NULL
);

CREATE TABLE students (
    student_id INT IDENTITY(1,1) CONSTRAINT PK_students PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    enrollment_date DATE NOT NULL
);

CREATE TABLE enrollments (
    enrollment_id INT IDENTITY(1,1) CONSTRAINT PK_enrollments PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_status VARCHAR(50) NOT NULL DEFAULT 'Active',
    CONSTRAINT FK_enrollments_student FOREIGN KEY (student_id) REFERENCES students(student_id),
    CONSTRAINT FK_enrollments_course FOREIGN KEY (course_id) REFERENCES courses(course_id)
);


INSERT INTO courses (course_name, category, duration_weeks)
VALUES
('SQL Basico', 'Technology', 10),
('Java', 'Technology', 15);


INSERT INTO students (full_name, enrollment_date)
VALUES
('Maria Sousa', '2024-12-27'),
('João Barros', '2025-01-16'),
('Ana Lucia', '2024-11-17');


INSERT INTO enrollments (student_id, course_id, enrollment_status)
VALUES
(1, 1, 'Active'),     -- Maria Sousa no curso SQL Basico
(2, 1, 'Active'),     -- João Barros no curso SQL Basico
(3, 2, 'Active'),     -- Ana Lucia no curso Java
(1, 2, 'Inactive');   -- Maria Sousa desativada no curso Java


SELECT s.full_name, c.course_name, e.enrollment_status
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN courses c ON e.course_id = c.course_id
WHERE s.full_name = 'Maria Sousa';