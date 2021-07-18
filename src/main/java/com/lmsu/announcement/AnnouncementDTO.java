package com.lmsu.announcement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor

@Data
public class AnnouncementDTO implements Serializable {
    private int id;
    private Date creationDate;
    private String announcementText;
    private String writerId;
    private Date returnDeadline;
}
