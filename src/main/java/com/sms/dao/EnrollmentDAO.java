package com.sms.dao;

import com.sms.exception.DataAccessException;
import com.sms.model.Enrollment;
import com.sms.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for enrollments (the student<->course join table).
 * List queries JOIN students and courses so the UI can show names/codes.
 */
public class EnrollmentDAO {

    private static final String SELECT_WITH_JOINS =
            "SELECT e.id, e.student_id, e.course_id, e.enrollment_date, "
          + "CONCAT(s.first_name,' ',s.last_name) AS student_name, "
          + "c.course_name, c.course_code "
          + "FROM enrollments e "
          + "JOIN students s ON s.id = e.student_id "
          + "JOIN courses  c ON c.id = e.course_id ";

    public List<Enrollment> findAll() {
        String sql = SELECT_WITH_JOINS + "ORDER BY e.id";
        List<Enrollment> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("findAll enrollments failed", e);
        }
        return list;
    }

    /** Enrollments for one student (used by "View Student Courses"). */
    public List<Enrollment> findByStudent(int studentId) {
        String sql = SELECT_WITH_JOINS + "WHERE e.student_id = ? ORDER BY e.id";
        List<Enrollment> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("findByStudent enrollments failed", e);
        }
        return list;
    }

    /** True if this student is already enrolled in this course. */
    public boolean exists(int studentId, int courseId) {
        String sql = "SELECT 1 FROM enrollments WHERE student_id=? AND course_id=?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("exists enrollment check failed", e);
        }
    }

    public void insert(Enrollment en) {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date) "
                + "VALUES (?, ?, ?)";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, en.getStudentId());
            ps.setInt(2, en.getCourseId());
            ps.setDate(3, Date.valueOf(en.getEnrollmentDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("insert enrollment failed: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM enrollments WHERE id=?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("delete enrollment failed", e);
        }
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM enrollments";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("count enrollments failed", e);
        }
        return 0;
    }

    private Enrollment map(ResultSet rs) throws SQLException {
        Enrollment en = new Enrollment();
        en.setId(rs.getInt("id"));
        en.setStudentId(rs.getInt("student_id"));
        en.setCourseId(rs.getInt("course_id"));
        Date d = rs.getDate("enrollment_date");
        en.setEnrollmentDate(d == null ? null : d.toLocalDate());
        en.setStudentName(rs.getString("student_name"));
        en.setCourseName(rs.getString("course_name"));
        en.setCourseCode(rs.getString("course_code"));
        return en;
    }
}
