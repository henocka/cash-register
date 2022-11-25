package com.cfcook.cashregister.dto;

import com.cfcook.cashregister.entity.CashType;
import com.cfcook.cashregister.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeResponse {
    private long registerId;
    private String registerName;
    private State currentState;
    private Map<CashType, Integer> changeByDenominations;
    private int requestedCash;
    private String message;
}
