-- ============================================================
-- Student Management System - Schema + Seed Data
-- ============================================================

CREATE DATABASE IF NOT EXISTS student_management_system;
USE student_management_system;

DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS students;

CREATE TABLE students (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(50)  NOT NULL,
  last_name  VARCHAR(50)  NOT NULL,
  email      VARCHAR(100) NOT NULL,
  phone      VARCHAR(20),
  dob        DATE,
  CONSTRAINT uq_students_email UNIQUE (email)
);

CREATE TABLE courses (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  course_name VARCHAR(100) NOT NULL,
  course_code VARCHAR(20)  NOT NULL,
  credits     INT          NOT NULL,
  CONSTRAINT uq_courses_code UNIQUE (course_code),
  CONSTRAINT chk_courses_credits CHECK (credits > 0)
);

CREATE TABLE enrollments (
  id              INT AUTO_INCREMENT PRIMARY KEY,
  student_id      INT NOT NULL,
  course_id       INT NOT NULL,
  enrollment_date DATE NOT NULL,
  CONSTRAINT fk_enroll_student
      FOREIGN KEY (student_id) REFERENCES students(id)
      ON DELETE CASCADE,
  CONSTRAINT fk_enroll_course
      FOREIGN KEY (course_id) REFERENCES courses(id)
      ON DELETE CASCADE,
  CONSTRAINT uq_enroll_student_course UNIQUE (student_id, course_id)
);

-- 10 students
INSERT INTO students (first_name, last_name, email, phone, dob) VALUES
('Aarav',  'Sharma',   'aarav.sharma@example.com',   '9876500001', '2002-01-15'),
('Diya',   'Patel',    'diya.patel@example.com',     '9876500002', '2001-03-22'),
('Vivaan', 'Reddy',    'vivaan.reddy@example.com',   '9876500003', '2003-07-09'),
('Ananya', 'Iyer',     'ananya.iyer@example.com',    '9876500004', '2002-11-30'),
('Kabir',  'Nair',     'kabir.nair@example.com',     '9876500005', '2000-05-18'),
('Ishita', 'Gupta',    'ishita.gupta@example.com',   '9876500006', '2001-09-12'),
('Arjun',  'Mehta',    'arjun.mehta@example.com',    '9876500007', '2003-02-25'),
('Saanvi', 'Joshi',    'saanvi.joshi@example.com',   '9876500008', '2002-06-03'),
('Reyansh','Kulkarni', 'reyansh.kulkarni@example.com','9876500009', '2001-12-19'),
('Myra',   'Verma',    'myra.verma@example.com',     '9876500010', '2003-04-07');

-- 5 courses
INSERT INTO courses (course_name, course_code, credits) VALUES
('Introduction to Java',        'CS101', 4),
('Database Management Systems',  'CS102', 3),
('Web Development',              'CS103', 4),
('Data Structures',              'CS104', 4),
('Operating Systems',            'CS105', 3);

-- 10 enrollments
INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES
(1, 1, '2025-06-01'),
(1, 2, '2025-06-01'),
(2, 1, '2025-06-02'),
(3, 3, '2025-06-03'),
(4, 4, '2025-06-04'),
(5, 2, '2025-06-05'),
(6, 5, '2025-06-06'),
(7, 1, '2025-06-07'),
(8, 3, '2025-06-08'),
(9, 4, '2025-06-09');
