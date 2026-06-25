package com.sms.service;

import com.sms.dao.EnrollmentDAO;
import com.sms.model.Enrollment;

import java.time.LocalDate;
import java.util.List;

/**
 * Business layer for enrollments. Enforces the "one student per course only once"
 * rule before inserting, and exposes list/view operations.
 */
public class EnrollmentService {

    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public List<Enrollment> getAllEnrollments() {
        return enrollmentDAO.findAll();
    }

    public List<Enrollment> getEnrollmentsForStudent(int studentId) {
        return enrollmentDAO.findByStudent(studentId);
    }

    /**
     * Assign a student to a course.
     * Throws IllegalArgumentException if the pair already exists, so the
     * servlet can show a friendly message instead of a raw SQL error.
     */
    public void assign(int studentId, int courseId) {
        if (studentId <= 0 || courseId <= 0) {
            throw new IllegalArgumentException("Please select both a student and a course");
        }
        if (enrollmentDAO.exists(studentId, courseId)) {
            throw new IllegalArgumentException(
                    "This student is already enrolled in that course");
        }
        Enrollment en = new Enrollment(studentId, courseId, LocalDate.now());
        enrollmentDAO.insert(en);
    }

    public void removeEnrollment(int id) {
        enrollmentDAO.delete(id);
    }

    public long getEnrollmentCount() {
        return enrollmentDAO.findAll().stream().count();
    }
}
