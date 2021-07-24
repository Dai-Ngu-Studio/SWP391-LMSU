package com.lmsu.bean.renewal;

import com.lmsu.bean.orderdata.OrderItemObj;
import com.lmsu.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class RenewalRequestObj implements Serializable {
    private int renewalID;
    private OrderItemObj item;
    private UserDTO librarian;
    private String reason;
    private Date requestedExtendDate;
    private int approvalStatus;
}
