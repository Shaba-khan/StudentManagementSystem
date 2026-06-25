package com.sms.service;

import com.sms.model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for CourseService validation logic.
 */
class CourseServiceTest {

    private final CourseService service = new CourseService();

    private Course valid() {
        return new Course("Sample Course", "SC100", 3);
    }

    // ---- POSITIVE ----

    @Test
    void validCoursePassesValidation() {
        Course c = valid();
        try {
            service.addCourse(c);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Valid course should not fail validation", e);
        } catch (RuntimeException ignoredDbError) {
            // DB may be unavailable in a pure unit run.
        }
    }

    // ---- NEGATIVE ----

    @Test
    void blankNameIsRejected() {
        Course c = valid();
        c.setCourseName("");
        assertThrows(IllegalArgumentException.class, () -> service.addCourse(c));
    }

    @Test
    void blankCodeIsRejected() {
        Course c = valid();
        c.setCourseCode("  ");
        assertThrows(IllegalArgumentException.class, () -> service.addCourse(c));
    }

    @Test
    void zeroCreditsIsRejected() {
        Course c = valid();
        c.setCredits(0);
        assertThrows(IllegalArgumentException.class, () -> service.addCourse(c));
    }

    @Test
    void negativeCreditsIsRejected() {
        Course c = valid();
        c.setCredits(-2);
        assertThrows(IllegalArgumentException.class, () -> service.addCourse(c));
    }
}
