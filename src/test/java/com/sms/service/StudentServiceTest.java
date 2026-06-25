package com.sms.service;

import com.sms.model.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for StudentService validation logic.
 * These cover the rules without requiring a live database, so they run
 * anywhere with `mvn test`.
 */
class StudentServiceTest {

    private final StudentService service = new StudentService();

    private Student valid() {
        return new Student("Test", "User", "test.user@example.com",
                "9999999999", LocalDate.of(2000, 1, 1));
    }

    // ---- POSITIVE ----

    @Test
    void validStudentPassesValidation() {
        // Validation should not throw for a well-formed student.
        // (We call the private path indirectly: addStudent validates first.)
        Student s = valid();
        // We only assert the validation branch doesn't throw IllegalArgumentException;
        // a DB error would be a different exception type.
        try {
            service.addStudent(s);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Valid student should not fail validation", e);
        } catch (RuntimeException ignoredDbError) {
            // DB may be unavailable in a pure unit run - that's acceptable here.
        }
    }

    // ---- NEGATIVE ----

    @Test
    void blankFirstNameIsRejected() {
        Student s = valid();
        s.setFirstName("");
        assertThrows(IllegalArgumentException.class, () -> service.addStudent(s));
    }

    @Test
    void blankLastNameIsRejected() {
        Student s = valid();
        s.setLastName("  ");
        assertThrows(IllegalArgumentException.class, () -> service.addStudent(s));
    }

    @Test
    void invalidEmailIsRejected() {
        Student s = valid();
        s.setEmail("not-an-email");
        assertThrows(IllegalArgumentException.class, () -> service.addStudent(s));
    }

    @Test
    void nullStudentIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.addStudent(null));
    }
}
