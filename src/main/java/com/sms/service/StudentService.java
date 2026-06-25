package com.sms.service;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Business layer for students.
 * Demonstrates the Java Collections Framework (List, Set, Map) and the
 * Stream API (filter, sorted, map, count, collect) as required by the assignment.
 */
public class StudentService {

    private final StudentDAO studentDAO = new StudentDAO();

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public Student getStudent(int id) {
        return studentDAO.findById(id);
    }

    public void addStudent(Student s) {
        validate(s);
        studentDAO.insert(s);
    }

    public void updateStudent(Student s) {
        validate(s);
        studentDAO.update(s);
    }

    public void deleteStudent(int id) {
        studentDAO.delete(id);
    }

    /**
     * Search. If the keyword is blank, returns all.
     * Uses Stream API to trim and filter the DB result defensively.
     */
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllStudents();
        }
        String k = keyword.trim().toLowerCase();
        // STREAM API: filter students whose name/email contains the keyword
        return studentDAO.findAll().stream()
                .filter(s -> s.getFirstName().toLowerCase().contains(k)
                        || s.getLastName().toLowerCase().contains(k)
                        || s.getEmail().toLowerCase().contains(k))
                .sorted(Comparator.comparing(Student::getFirstName))
                .collect(Collectors.toList());
    }

    /** STREAM API example: students sorted by last name then first name. */
    public List<Student> getStudentsSorted() {
        return studentDAO.findAll().stream()
                .sorted(Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getFirstName))
                .collect(Collectors.toList());
    }

    /** COLLECTIONS (Set) + STREAM: distinct email domains among students. */
    public Set<String> getEmailDomains() {
        return studentDAO.findAll().stream()
                .map(s -> s.getEmail().substring(s.getEmail().indexOf('@') + 1))
                .collect(Collectors.toSet());
    }

    /** COLLECTIONS (Map) + STREAM: count students grouped by last-name initial. */
    public Map<Character, Long> countByLastNameInitial() {
        return studentDAO.findAll().stream()
                .collect(Collectors.groupingBy(
                        s -> Character.toUpperCase(s.getLastName().charAt(0)),
                        Collectors.counting()));
    }

    public long getStudentCount() {
        // STREAM API: count()
        return studentDAO.findAll().stream().count();
    }

    // ---- validation ----

    private void validate(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (isBlank(s.getFirstName())) {
            throw new IllegalArgumentException("First name is required");
        }
        if (isBlank(s.getLastName())) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (isBlank(s.getEmail()) || !s.getEmail().contains("@")) {
            throw new IllegalArgumentException("A valid email is required");
        }
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}
