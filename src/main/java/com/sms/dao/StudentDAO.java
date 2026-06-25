package com.sms.dao;

import com.sms.exception.DataAccessException;
import com.sms.model.Student;
import com.sms.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for students. All SQL for the students table lives here.
 * Uses PreparedStatement everywhere to prevent SQL injection.
 */
public class StudentDAO {

    public List<Student> findAll() {
        String sql = "SELECT id, first_name, last_name, email, phone, dob "
                + "FROM students ORDER BY id";
        List<Student> list = new ArrayList<>();
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DataAccessException("findAll students failed", e);
        }
        return list;
    }

    public Student findById(int id) {
        String sql = "SELECT id, first_name, last_name, email, phone, dob "
                + "FROM students WHERE id = ?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("findById student failed", e);
        }
        return null;
    }

    public void insert(Student s) {
        String sql = "INSERT INTO students (first_name, last_name, email, phone, dob) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bind(ps, s);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    s.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("insert student failed: " + e.getMessage(), e);
        }
    }

    public void update(Student s) {
        String sql = "UPDATE students SET first_name=?, last_name=?, email=?, "
                + "phone=?, dob=? WHERE id=?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            bind(ps, s);
            ps.setInt(6, s.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("update student failed: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("delete student failed", e);
        }
    }

    /** Case-insensitive search across name and email. */
    public List<Student> search(String keyword) {
        String sql = "SELECT id, first_name, last_name, email, phone, dob FROM students "
                + "WHERE LOWER(first_name) LIKE ? OR LOWER(last_name) LIKE ? "
                + "OR LOWER(email) LIKE ? ORDER BY id";
        List<Student> list = new ArrayList<>();
        String like = "%" + keyword.toLowerCase() + "%";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("search students failed", e);
        }
        return list;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM students";
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("count students failed", e);
        }
        return 0;
    }

    // ---- helpers ----

    private void bind(PreparedStatement ps, Student s) throws SQLException {
        ps.setString(1, s.getFirstName());
        ps.setString(2, s.getLastName());
        ps.setString(3, s.getEmail());
        ps.setString(4, s.getPhone());
        ps.setDate(5, s.getDob() == null ? null : Date.valueOf(s.getDob()));
    }

    private Student map(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        Date d = rs.getDate("dob");
        s.setDob(d == null ? null : d.toLocalDate());
        return s;
    }
}
