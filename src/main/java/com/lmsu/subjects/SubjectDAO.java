package com.lmsu.subjects;

import com.lmsu.authors.AuthorDTO;
import com.lmsu.books.BookDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO implements Serializable {
    private List<SubjectDTO> subjectList;

    public List<SubjectDTO> getSubjectList() {
        return this.subjectList;
    }

    public void clearSubjectList() {
        if (this.subjectList != null) {
            this.subjectList.clear();
        }
    }
    public ArrayList<SubjectDTO> getListSubject(String name) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<SubjectDTO> list = new ArrayList<>();

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name] "
                        + "FROM [Subjects] "
                        + "WHERE [name] LIKE ? AND [deleteStatus] = 0";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + name + "%");
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet then returns author name
                while (rs.next()) {
                    String id = rs.getString("id");
                    String nameCol = rs.getString("name");
                    SubjectDTO dto = new SubjectDTO(id, nameCol);
                    list.add(dto);
                }
            } //end if connection existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }
    public void viewSubjectList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [name], [semester_no], [deleteStatus] " +
                        "FROM Subjects " +
                        "WHERE [deleteStatus] = 0";
                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    int semester_no = rs.getInt("semester_no");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");

                    SubjectDTO dto = new SubjectDTO(id, name, semester_no, deleteStatus);
                    if (this.subjectList == null) {
                        this.subjectList = new ArrayList<SubjectDTO>();
                    }
                    this.subjectList.add(dto);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void searchSubjectByName(String searchVal) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [name], [semester_no], [deleteStatus] " +
                        "FROM [Subjects] " +
                        "WHERE [name] LIKE ? AND [deleteStatus] = 0";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchVal + "%");

                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    int semester_no = rs.getInt("semester_no");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");

                    SubjectDTO dto = new SubjectDTO(id, name, semester_no, deleteStatus);
                    if (this.subjectList == null) {
                        this.subjectList = new ArrayList<SubjectDTO>();
                    }
                    this.subjectList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public void searchSubjectBySemester(int semester_no) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [name], [semester_no], [deleteStatus] " +
                        "FROM [Subjects] " +
                        "WHERE [semester_no] LIKE ? AND [deleteStatus] = 0";
                stm = con.prepareStatement(sql);
                stm.setInt(1, semester_no);

                rs = stm.executeQuery();
                while (rs.next()) {
                    if (this.subjectList == null) {
                        this.subjectList = new ArrayList<SubjectDTO>();
                    }
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    int semester_noVal = rs.getInt("semester_no");
                    boolean deleteStatus = rs.getBoolean("deleteStatus");
                    SubjectDTO dto = new SubjectDTO(id, name, semester_no, deleteStatus);
                    this.subjectList.add(dto);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean addSubject(String subjectID, String subjectName, int semester_no, boolean deleteStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Subjects([id], [name], [semester_no], [deleteStatus]) " +
                        "VALUES(?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, subjectID);
                stm.setString(2, subjectName);
                stm.setInt(3, semester_no);
                stm.setBoolean(4, deleteStatus);
                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }

        return false;
    }

    public boolean deleteSubject(String subjectID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Subjects] " +
                        "SET [deleteStatus] = ? " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setString(2, subjectID);
                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updateSubject(String subjectID, String subjectName, int semester_no)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Subjects] " +
                        "SET [name] = ?, " +
                        "[semester_no] = ? " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);

                stm.setString(1, subjectName);
                stm.setInt(2, semester_no);
                stm.setString(3, subjectID);

                //4. Execute Query and get rows affected
                int rows = stm.executeUpdate();
                //5. Process result
                if (rows > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean checkSubjectExisted(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select [id] "
                        + "from [Subjects] "
                        + "where [id] LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
    public SubjectDTO getById(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT * " +
                        "FROM [Subjects] " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    String nameCol = rs.getString("name");
                    SubjectDTO dto = new SubjectDTO(id, nameCol);
                    return dto;
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return null;
    }
}
