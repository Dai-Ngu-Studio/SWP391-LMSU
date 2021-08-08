package com.lmsu.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO implements Serializable {
    private int feedbackID;
    private String fullName;
    private String email;
    private String phone;
    private String feedbackType;
    private String attachment;
    private String feedbackMessage;
    private boolean resolveStatus;
}
