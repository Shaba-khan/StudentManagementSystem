package com.sms.dao;

import com.sms.exception.DataAccessException;
import com.sms.model.Course;
import com.sms.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for courses. All SQL for the courses table lives here.
 */
public class CourseDAO {

    public List<Course> findAll() {
        String sql = "SELECT id, course_name, course_code, credits "
                + "FROM courses ORDER BY id";
        List<Course> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("findAll courses failed", e);
        }
        return list;
    }

    public Course findById(int id) {
        String sql = "SELECT id, course_name, course_code, credits "
                + "FROM courses WHERE id = ?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("findById course failed", e);
        }
        return null;
    }

    public void insert(Course c) {
        String sql = "INSERT INTO courses (course_name, course_code, credits) "
                + "VALUES (?, ?, ?)";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getCourseCode());
            ps.setInt(3, c.getCredits());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    c.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("insert course failed: " + e.getMessage(), e);
        }
    }

    public void update(Course c) {
        String sql = "UPDATE courses SET course_name=?, course_code=?, credits=? WHERE id=?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCourseName());
            ps.setString(2, c.getCourseCode());
            ps.setInt(3, c.getCredits());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("update course failed: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM courses WHERE id=?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("delete course failed", e);
        }
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM courses";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("count courses failed", e);
        }
        return 0;
    }

    private Course map(ResultSet rs) throws SQLException {
        Course c = new Course();
        c.setId(rs.getInt("id"));
        c.setCourseName(rs.getString("course_name"));
        c.setCourseCode(rs.getString("course_code"));
        c.setCredits(rs.getInt("credits"));
        return c;
    }
}
