package com.sms.model;

import java.time.LocalDate;

/**
 * Plain data object representing an enrollment record.
 * Mirrors the 'enrollments' table, plus extra read-only fields
 * (studentName, courseName, courseCode) populated from JOINs for display.
 */
public class Enrollment {

    private int id;
    private int studentId;
    private int courseId;
    private LocalDate enrollmentDate;

    // Display-only fields, filled by JOIN queries
    private String studentName;
    private String courseName;
    private String courseCode;

    public Enrollment() {
    }

    public Enrollment(int id, int studentId, int courseId, LocalDate enrollmentDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public Enrollment(int studentId, int courseId, LocalDate enrollmentDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return "Enrollment{id=" + id + ", studentId=" + studentId
                + ", courseId=" + courseId + ", enrollmentDate=" + enrollmentDate + "}";
    }
}
