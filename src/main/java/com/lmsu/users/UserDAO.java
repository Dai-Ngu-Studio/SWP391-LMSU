package com.lmsu.users;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements Serializable {

    private List<UserDTO> listAccount;

    public List<UserDTO> getListAccount() {
        return listAccount;
    }

    public UserDTO checkLogin(String email, String password)
            throws SQLException, NamingException {
        UserDTO user = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "select [id], [name], [roleID],[semester_no], [email], [phoneNumber], [profilePicturePath] "
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
                    String emailCol = rs.getString("email");
                    String phoneNumber = rs.getString("phoneNumber");
                    String profilePicturePath = rs.getString("profilePicturePath");
                    user = new UserDTO(id, name, roleID, semester_no, "", "", emailCol, phoneNumber, profilePicturePath);
                }
            }
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
        return user;
    }
}

