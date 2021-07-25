package com.lmsu.feedback;



import com.lmsu.authors.AuthorDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO implements Serializable {
    private List<FeedbackDTO> feedbackList;

    public List<FeedbackDTO> getFeedbackList(){
        return this.feedbackList;
    }

    public void viewFeedbackList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT [id], [fullName], [email], [phone], [feedbackType], [attachment], [feedbackMessage], [resolveStatus] " +
                        "FROM Feedback ";
                stm = con.prepareStatement(sql);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("fullName");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    String feedbackType = rs.getString("feedbackType");
                    String attachment = rs.getString("attachment");
                    String feedbackMsg = rs.getNString("feedbackMessage");
                    boolean resolveStatus = rs.getBoolean("resolveStatus");

                    FeedbackDTO dto = new FeedbackDTO(id, name, email, phone, feedbackType, attachment, feedbackMsg, resolveStatus);
                    if (this.feedbackList == null) {
                        this.feedbackList = new ArrayList<FeedbackDTO>();
                    }
                    this.feedbackList.add(dto);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean checkFeedbackId(int feedbackID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id] " +
                        "FROM [Feedback] " +
                        "WHERE [id] LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setInt(1, feedbackID);
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

    public boolean addFeedback(String fullName, String email, String phone, String feedbackType,
                               String attachment, String feedbackMsg, boolean resolveStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO Feedback([fullName], [email], [phone], [feedbackType], [attachment], [feedbackMessage], [resolveStatus]) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, fullName);
                stm.setString(2, email);
                stm.setString(3, phone);
                stm.setString(4, feedbackType);
                stm.setString(5, attachment);
                stm.setString(6, feedbackMsg);
                stm.setBoolean(7, resolveStatus);
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

    public boolean updateFeedbackStatus(int feedbackID, boolean resolveStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Feedback] " +
                        "SET [resolveStatus] = ? " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);

                stm.setBoolean(1, resolveStatus);
                stm.setInt(2, feedbackID);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }
}
