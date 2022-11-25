package com.cfcook.cashregister.dto;

import com.cfcook.cashregister.entity.CashType;
import com.cfcook.cashregister.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private State state;
    private Map<String, Integer> cashAmount;
}
