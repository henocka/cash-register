package com.cfcook.cashregister.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashValue {
    @Id
    @GeneratedValue
    private Long id;


    @Enumerated(EnumType.ORDINAL)
    private CashType cashType;

}
