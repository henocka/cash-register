package com.cfcook.cashregister.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private State state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cash_id", referencedColumnName = "id")
    private Cash currentCashBalance;

    private int totalValue;
}
