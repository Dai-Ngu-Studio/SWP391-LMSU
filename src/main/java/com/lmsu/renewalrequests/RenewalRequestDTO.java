package com.lmsu.renewalrequests;

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
    private String librarianID;
    private String reason;
    private Date requestedExtendDate;
    private int approvalStatus;
}
