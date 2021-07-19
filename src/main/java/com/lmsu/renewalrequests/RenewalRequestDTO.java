package com.lmsu.renewalrequests;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RenewalRequestDTO implements Serializable {
    private String renewalID;
    private int itemID;
    private String librarianID;
    private String reason;
    private Date requestedExtendDate;
    private int approvalStatus;

    public RenewalRequestDTO(){ }

    public RenewalRequestDTO(String renewalID, int itemID, String librarianID, String reason,
                             Date requestedExtendDate, int approvalStatus){
        this.renewalID = renewalID;
        this.itemID = itemID;
        this.librarianID = librarianID;
        this.reason = reason;
        this.requestedExtendDate = requestedExtendDate;
        this.approvalStatus = approvalStatus;
    }
}
