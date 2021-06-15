package com.lmsu.orderdata.orderitems;

import com.lmsu.books.BookDTO;
import com.lmsu.utils.DBHelpers;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO implements Serializable {

    private List<OrderItemDTO> orderItemList;

    public List<OrderItemDTO> getOrderItemList() {
        return this.orderItemList;
    }

    public void viewOrderItems() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                String sql = "SELECT OrderItems.id, [title], [orderID], [bookID], [lendStatus], [returnDeadline], [lendDate], [returnDate] " +
                        "FROM OrderItems, Books " +
                        "WHERE OrderItems.bookID = Books.id";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int orderID = rs.getInt("orderID");
                    String memberID = rs.getString("memberID");
                    String bookID = rs.getString("bookID");
                    int lendStatus = rs.getInt("lendStatus");
                    Date returnDeadline = rs.getDate("returnDeadline");
                    Date lendDate = rs.getDate("lendDate");
                    Date returnDate = rs.getDate("returnDate");
                    OrderItemDTO dto = new OrderItemDTO(id, orderID, memberID, bookID, lendStatus, returnDeadline, lendDate, returnDate);

                    if (this.orderItemList == null) {
                        this.orderItemList = new ArrayList<OrderItemDTO>();
                    }
                    this.orderItemList.add(dto);
                }
            }

        } finally {
            if (rs != null) rs.close();
            if (stm != null) stm.close();
            if (con != null) con.close();
        }
    }
}
