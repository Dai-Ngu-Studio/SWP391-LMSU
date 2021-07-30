package com.lmsu.renewalrequests;

import com.lmsu.orderdata.orderitems.OrderItemDTO;
import com.lmsu.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class RenewalRequestDTO implements Serializable {
    private int renewalID;
    private int itemID;
    private OrderItemDTO orderItem;
    private String librarianID;
    private UserDTO librarian;
    private String reason;
    private Date requestedExtendDate;
    private int approvalStatus;
}
