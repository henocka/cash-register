package com.cfcook.cashregister.dto;

import com.cfcook.cashregister.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequest {
    private State state;
    private int change;
}
