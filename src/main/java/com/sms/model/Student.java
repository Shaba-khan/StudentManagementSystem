package com.sms.model;

import java.time.LocalDate;

/**
 * Plain data object representing a student record.
 * Mirrors the 'students' table.
 */
public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;

    public Student() {
    }

    public Student(int id, String firstName, String lastName,
                   String email, String phone, LocalDate dob) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }

    public Student(String firstName, String lastName,
                   String email, String phone, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Convenience for display in JSP: "First Last". */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + "}";
    }
}
