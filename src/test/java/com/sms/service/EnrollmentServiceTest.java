package com.sms.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for EnrollmentService guard logic.
 * The "already enrolled" duplicate path needs a DB, so here we cover the
 * input-guard branch (invalid ids) which is DB-independent.
 */
class EnrollmentServiceTest {

    private final EnrollmentService service = new EnrollmentService();

    // ---- NEGATIVE ----

    @Test
    void zeroStudentIdIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.assign(0, 1));
    }

    @Test
    void zeroCourseIdIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.assign(1, 0));
    }

    @Test
    void negativeIdsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.assign(-1, -1));
    }
}
