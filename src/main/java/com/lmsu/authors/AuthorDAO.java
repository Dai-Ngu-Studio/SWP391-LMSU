package com.lmsu.authors;

import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDAO implements Serializable {
    public String getAuthorName(String authorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con!=null) {
                //2. Create SQL String
                String sql = "SELECT [name] " +
                        "FROM [Authors] " +
                        "WHERE [id] = ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, authorID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet then returns author name
                if (rs.next()) return rs.getString("name");
            } //end if connection existed
        } finally {
            if (rs!=null) rs.close();
            if (stm!=null) stm.close();
            if (con!=null) con.close();
        }
        return null;
    }
}
