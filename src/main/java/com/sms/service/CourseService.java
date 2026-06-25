package com.sms.service;

import com.sms.dao.CourseDAO;
import com.sms.model.Course;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business layer for courses, with validation and Stream API usage.
 */
public class CourseService {

    private final CourseDAO courseDAO = new CourseDAO();

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public Course getCourse(int id) {
        return courseDAO.findById(id);
    }

    public void addCourse(Course c) {
        validate(c);
        courseDAO.insert(c);
    }

    public void updateCourse(Course c) {
        validate(c);
        courseDAO.update(c);
    }

    public void deleteCourse(int id) {
        courseDAO.delete(id);
    }

    /** STREAM API: courses with credits >= the given threshold, sorted by name. */
    public List<Course> getCoursesByMinCredits(int minCredits) {
        return courseDAO.findAll().stream()
                .filter(c -> c.getCredits() >= minCredits)
                .sorted(Comparator.comparing(Course::getCourseName))
                .collect(Collectors.toList());
    }

    /** STREAM API: sum of all credits offered. */
    public int getTotalCredits() {
        return courseDAO.findAll().stream()
                .mapToInt(Course::getCredits)
                .sum();
    }

    public long getCourseCount() {
        return courseDAO.findAll().stream().count();
    }

    private void validate(Course c) {
        if (c == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        if (isBlank(c.getCourseName())) {
            throw new IllegalArgumentException("Course name is required");
        }
        if (isBlank(c.getCourseCode())) {
            throw new IllegalArgumentException("Course code is required");
        }
        if (c.getCredits() <= 0) {
            throw new IllegalArgumentException("Credits must be greater than 0");
        }
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}
