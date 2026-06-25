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
 * Dashboard controller. Computes the three totals and forwards to the view.
 * URL: /dashboard
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final EnrollmentService enrollmentService = new EnrollmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("totalStudents", studentService.getStudentCount());
        req.setAttribute("totalCourses", courseService.getCourseCount());
        req.setAttribute("totalEnrollments", enrollmentService.getEnrollmentCount());
        req.getRequestDispatcher("/jsp/dashboard.jsp").forward(req, resp);
    }
}
