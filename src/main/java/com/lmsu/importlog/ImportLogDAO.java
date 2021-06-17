package com.lmsu.importlog;

import com.lmsu.books.BookDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportLogDAO implements Serializable {

    private List<ImportLogDTO> importList;

    public List<ImportLogDTO> getImportList(){
        return this.importList;
    }

    public void viewImportList() throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [bookID], [managerID], [dateTaken], [supplier], [quantity] " +
                        "FROM [ImportLogs]";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    int logID = rs.getInt("id");
                    String bookID = rs.getString("bookID");
                    String managerID = rs.getString("managerID");
                    Date dateTaken = rs.getDate("dateTaken");
                    String supplier = rs.getString("supplier");
                    int quantity = rs.getInt("quantity");

                    ImportLogDTO dto = new ImportLogDTO(logID, bookID, managerID, dateTaken, supplier, quantity);

                    if (this.importList == null) {
                        this.importList = new ArrayList<ImportLogDTO>();
                    } //end if book list not existed
                    this.importList.add(dto);
                } //end while traversing result set
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }

    public boolean addLog(String bookID, String managerID, Date dateTaken, String supplier,
                          int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO " +
                        "ImportLogs([bookID], [managerID], [dateTaken], [supplier], [quantity] ) " +
                        "VALUES(?, ?, ?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, bookID);
                stm.setString(2, managerID);
                stm.setDate(3, dateTaken);
                stm.setString(4, supplier);
                stm.setInt(5, quantity);

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

    public void searchLogBySupplier(String searchValue) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT [id], [bookID], [managerID], [dateTaken], [supplier], [quantity] " +
                        "FROM [ImportLogs] " +
                        "WHERE [supplier] LIKE ? ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    int logID = rs.getInt("id");
                    String bookID = rs.getString("bookID");
                    String managerID = rs.getString("managerID");
                    Date dateTaken = rs.getDate("dateTaken");
                    String supplier = rs.getString("supplier");
                    int quantity = rs.getInt("quantity");
                    ImportLogDTO dto = new ImportLogDTO(logID, bookID, managerID, dateTaken, supplier, quantity);
                    if (this.importList == null) {
                        this.importList = new ArrayList<ImportLogDTO>();
                    } //end if bookList not existed
                    this.importList.add(dto);
                } //end while traversing result
            } //end if connection existed
        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
