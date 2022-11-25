package com.cfcook.cashregister.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cash {
    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cash_id", referencedColumnName = "id")
    @MapKey(name = "cashType")
    private Map<CashType, CashValue> cashAmount;
}
