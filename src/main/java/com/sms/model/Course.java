package com.sms.model;

/**
 * Plain data object representing a course record.
 * Mirrors the 'courses' table.
 */
public class Course {

    private int id;
    private String courseName;
    private String courseCode;
    private int credits;

    public Course() {
    }

    public Course(int id, String courseName, String courseCode, int credits) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
    }

    public Course(String courseName, String courseCode, int credits) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Course{id=" + id + ", courseName=" + courseName
                + ", courseCode=" + courseCode + ", credits=" + credits + "}";
    }
}
