package com.sms.controller;

import com.sms.model.Course;
import com.sms.service.CourseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Front controller for course actions.
 * URL: /course?action=list|new|edit|save|delete
 */
@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = param(req, "action", "list");
        switch (action) {
            case "new"    -> showForm(req, resp, null);
            case "edit"   -> showForm(req, resp, courseService.getCourse(
                                Integer.parseInt(req.getParameter("id"))));
            case "delete" -> delete(req, resp);
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
        req.setAttribute("courses", courseService.getAllCourses());
        forward(req, resp, "/jsp/course-list.jsp");
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, Course c)
            throws ServletException, IOException {
        req.setAttribute("course", c);
        forward(req, resp, "/jsp/course-form.jsp");
    }

    private void save(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idStr = req.getParameter("id");
            Course c = new Course();
            c.setCourseName(param(req, "courseName", ""));
            c.setCourseCode(param(req, "courseCode", ""));
            String credits = param(req, "credits", "0");
            c.setCredits(credits.isBlank() ? 0 : Integer.parseInt(credits));

            if (idStr != null && !idStr.isBlank()) {
                c.setId(Integer.parseInt(idStr));
                courseService.updateCourse(c);
            } else {
                courseService.addCourse(c);
            }
            resp.sendRedirect(req.getContextPath() + "/course?action=list");
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            Course c = new Course();
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isBlank()) {
                c.setId(Integer.parseInt(idStr));
            }
            c.setCourseName(req.getParameter("courseName"));
            c.setCourseCode(req.getParameter("courseCode"));
            req.setAttribute("course", c);
            forward(req, resp, "/jsp/course-form.jsp");
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        courseService.deleteCourse(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/course?action=list");
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
