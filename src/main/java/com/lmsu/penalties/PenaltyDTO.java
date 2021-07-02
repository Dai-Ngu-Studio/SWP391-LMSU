package com.lmsu.penalties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor

public @Data
class PenaltyDTO {
    private int itemID;
    private BigDecimal penaltyAmount;
    private boolean penaltyStatus;
}
