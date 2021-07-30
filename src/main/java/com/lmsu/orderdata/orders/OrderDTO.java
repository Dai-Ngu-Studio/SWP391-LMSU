package com.lmsu.orderdata.orders;

import com.lmsu.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class OrderDTO implements Serializable {
    /**
     * @param lendMethod false = direct, true = delivery
     */
    private int id;
    private String memberID;
    private UserDTO member;
    private Date orderDate;
    private boolean lendMethod;
    private int activeStatus;
}
