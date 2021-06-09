package com.lmsu.users;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements Serializable {

    private List<UserDTO> listAccount;

    public List<UserDTO> getListAccount() {
        return listAccount;
    }

    public UserDTO checkLogin(String email, String password) throws SQLException, NamingException {
        UserDTO user = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select [id], [name], [roleID],[semester_no], [password], [passwordGoogle], [email], [phoneNumber], [profilePicturePath] "
                        + "from [Users] "
                        + "where [email] = ? and [password] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String semester_no = rs.getString("semester_no");
                    String passwordCol = rs.getString("password");
                    String passwordGoogle = rs.getString("passwordGoogle");
                    String emailCol = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    user = new UserDTO(id, name, roleID, passwordCol, passwordGoogle, emailCol, phoneNumber, semester_no, profilePicturePath);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return user;
    }

    public void viewUserList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [roleID], [semester_no], [password], [passwordGoogle], [email], " +
                        "[phoneNumber], [profilePicturePath], [activeStatus] " +
                        "FROM [Users]";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    String passwordGoogle = rs.getString("passwordGoogle");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String semester = rs.getString("semester_no");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    UserDTO dto = new UserDTO(id, name, roleID, password, passwordGoogle, email, phoneNumber, semester, profilePicturePath, activeStatus);

                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<UserDTO>();
                    } //end if book list not existed
                    this.listAccount.add(dto);
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean updatePassword(String id, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [password] = ? " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, id);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }
        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public boolean updatePhone(String id, String phoneNumber) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "UPDATE [Users] " +
                        "SET [phoneNumber] = ? " +
                        "WHERE [id] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, phoneNumber);
                stm.setString(2, id);

                int row = stm.executeUpdate();
                if (row > 0) return true;
            }

        } finally {
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
        return false;
    }

    public void searchMemberByName(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [name], [roleID], [semester_no], [password], [passwordGoogle], [email], " +
                        "[phoneNumber], [profilePicturePath], [activeStatus] " +
                        "FROM [Users] " +
                        "WHERE [name] LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String roleID = rs.getString("roleID");
                    String password = rs.getString("password");
                    String passwordGoogle = rs.getString("passwordGoogle");
                    String email = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String semester = rs.getString("semester_no");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    boolean activeStatus = rs.getBoolean("activeStatus");
                    UserDTO dto = new UserDTO(id, name, roleID, password, passwordGoogle, email, phoneNumber, semester, profilePicturePath, activeStatus);

                    if (this.listAccount == null) {
                        this.listAccount = new ArrayList<UserDTO>();
                    } //end if book list not existed
                    this.listAccount.add(dto);
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}

