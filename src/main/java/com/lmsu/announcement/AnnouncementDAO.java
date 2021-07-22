package com.lmsu.announcement;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDAO implements Serializable {
    private List<AnnouncementDTO> announcementList;

    public List<AnnouncementDTO> getAnnouncementList() {
        return this.announcementList;
    }

    public void viewAnnouncementList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [creationDate], [announcementText], [writerId], [returnDeadline] " +
                        "FROM Announcement ";
                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date creationDate = rs.getDate("creationDate");
                    String announcementText = rs.getString("announcementText");
                    String writerId = rs.getString("writerId");
                    Date returnDeadline = rs.getDate("returnDeadline");

                    AnnouncementDTO dto = new AnnouncementDTO(id, creationDate, announcementText, writerId, returnDeadline);
                    if (this.announcementList == null) {
                        this.announcementList = new ArrayList<AnnouncementDTO>();
                    }
                    this.announcementList.add(dto);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean checkAnnouncementId(int announcementID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id] " +
                        "FROM [Announcement] " +
                        "WHERE [id] LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setInt(1, announcementID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) return true;
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean addAnnouncement(Date creationDate, String announcementText, String writerID, Date returnDeadline)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Announcement([creationDate], [announcementText], [writerID], [returnDeadline]) " +
                        "VALUES(?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setDate(1, creationDate);
                stm.setString(2, announcementText);
                stm.setString(3, writerID);
                stm.setDate(4, returnDeadline);
                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updateAnnouncement(int announcementID, Date creationDate, String announcementText, String writerID, Date returnDeadline)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "UPDATE [Announcement] " +
                        "SET [creationDate] = ?, " +
                        "[announcementText] = ?, " +
                        "[writerID] = ?, " +
                        "[returnDeadline] = ? " +
                        "WHERE [id] = ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);

                stm.setDate(1, creationDate);
                stm.setString(2, announcementText);
                stm.setString(3, writerID);
                stm.setDate(4, returnDeadline);
                stm.setInt(5, announcementID);

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

    public Date getReturnDate() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Date returnDate = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT returnDeadline FROM Announcement";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                if (rs.next()) {
                    Date returnDeadline = rs.getDate("returnDeadline");
                    returnDate = returnDeadline;
                }
            }
            return returnDate;
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public AnnouncementDTO getLatestAnnouncement() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [creationDate], [announcementText], [writerId], [returnDeadline] " +
                        "FROM Announcement " +
                        "WHERE [id] = (SELECT MAX([id]) FROM [Announcement]) ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date creationDate = rs.getDate("creationDate");
                    String announcementText = rs.getString("announcementText");
                    String writerId = rs.getString("writerId");
                    Date returnDeadline = rs.getDate("returnDeadline");
                    AnnouncementDTO dto = new AnnouncementDTO(id, creationDate, announcementText, writerId, returnDeadline);
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