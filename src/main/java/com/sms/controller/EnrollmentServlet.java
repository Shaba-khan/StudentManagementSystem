package com.sms.controller;

import com.sms.service.CourseService;
import com.sms.service.EnrollmentService;
import com.sms.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Front controller for enrollment actions.
 * URL: /enrollment?action=list|assign|remove
 */
@WebServlet("/enrollment")
public class EnrollmentServlet extends HttpServlet {

    private final EnrollmentService enrollmentService = new EnrollmentService();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = param(req, "action", "list");
        if ("remove".equals(action)) {
            remove(req, resp);
        } else {
            list(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        assign(req, resp);
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("enrollments", enrollmentService.getAllEnrollments());
        req.setAttribute("students", studentService.getAllStudents());
        req.setAttribute("courses", courseService.getAllCourses());
        forward(req, resp, "/jsp/enrollment-list.jsp");
    }

    private void assign(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int studentId = parseOrZero(req.getParameter("studentId"));
            int courseId = parseOrZero(req.getParameter("courseId"));
            enrollmentService.assign(studentId, courseId);
            resp.sendRedirect(req.getContextPath() + "/enrollment?action=list");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            list(req, resp);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        enrollmentService.removeEnrollment(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/enrollment?action=list");
    }

    private int parseOrZero(String v) {
        try {
            return v == null ? 0 : Integer.parseInt(v);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String param(HttpServletRequest req, String name, String def) {
        String v = req.getParameter(name);
        return v == null ? def : v;
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
