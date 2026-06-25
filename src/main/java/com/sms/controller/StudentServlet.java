package com.sms.controller;

import com.sms.model.Student;
import com.sms.service.EnrollmentService;
import com.sms.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Front controller for all student actions.
 * URL: /student?action=list|new|edit|save|delete|search|view
 */
@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final EnrollmentService enrollmentService = new EnrollmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = param(req, "action", "list");
        switch (action) {
            case "new"    -> showForm(req, resp, null);
            case "edit"   -> showForm(req, resp, getIdStudent(req));
            case "delete" -> delete(req, resp);
            case "search" -> search(req, resp);
            case "view"   -> view(req, resp);
            default        -> list(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        save(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("students", studentService.getAllStudents());
        forward(req, resp, "/jsp/student-list.jsp");
    }

    private void search(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String keyword = param(req, "keyword", "");
        req.setAttribute("students", studentService.searchStudents(keyword));
        req.setAttribute("keyword", keyword);
        forward(req, resp, "/jsp/student-list.jsp");
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Student s)
            throws ServletException, IOException {
        req.setAttribute("student", s);
        forward(req, resp, "/jsp/student-form.jsp");
    }

    private Student getIdStudent(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        return studentService.getStudent(id);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            Student s = new Student();
            s.setFirstName(param(req, "firstName", ""));
            s.setLastName(param(req, "lastName", ""));
            s.setEmail(param(req, "email", ""));
            s.setPhone(param(req, "phone", ""));
            String dob = req.getParameter("dob");
            s.setDob(dob == null || dob.isBlank() ? null : LocalDate.parse(dob));

            if (idStr != null && !idStr.isBlank()) {
                s.setId(Integer.parseInt(idStr));
                studentService.updateStudent(s);
            } else {
                studentService.addStudent(s);
            }
            resp.sendRedirect(req.getContextPath() + "/student?action=list");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("student", buildFromForm(req));
            forward(req, resp, "/jsp/student-form.jsp");
        }
    }

    private Student buildFromForm(HttpServletRequest req) {
        Student s = new Student();
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isBlank()) {
            s.setId(Integer.parseInt(idStr));
        }
        s.setFirstName(req.getParameter("firstName"));
        s.setLastName(req.getParameter("lastName"));
        s.setEmail(req.getParameter("email"));
        s.setPhone(req.getParameter("phone"));
        return s;
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        studentService.deleteStudent(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/student?action=list");
    }

    private void view(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Student s = studentService.getStudent(id);
        req.setAttribute("student", s);
        req.setAttribute("enrollments", enrollmentService.getEnrollmentsForStudent(id));
        forward(req, resp, "/jsp/student-view.jsp");
    }

    // ---- helpers ----

    private String param(HttpServletRequest req, String name, String def) {
        String v = req.getParameter(name);
        return v == null ? def : v;
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
