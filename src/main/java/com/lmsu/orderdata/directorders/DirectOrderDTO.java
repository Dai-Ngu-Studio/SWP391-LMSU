package com.lmsu.orderdata.directorders;

import com.lmsu.orderdata.orders.OrderDTO;
import com.lmsu.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor

public @Data
class DirectOrderDTO implements Serializable {
    private int orderID;
    private OrderDTO order;
    private String librarianID;
    private UserDTO librarian;
    private Timestamp scheduledTime;
    private boolean isReturnOrder;
}
