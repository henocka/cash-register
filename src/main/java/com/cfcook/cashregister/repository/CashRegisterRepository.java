package com.cfcook.cashregister.repository;

import com.cfcook.cashregister.entity.CashType;
import com.cfcook.cashregister.entity.CashValue;
import com.cfcook.cashregister.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CashRegisterRepository extends JpaRepository<Register, Long> {

    @Query("")
    Register updateCurrentBalance(long registerId, Map<CashType, CashValue> currentCashBalance);
}
