package com.cfcook.cashregister.service;

import com.cfcook.cashregister.entity.*;
import com.cfcook.cashregister.repository.CashRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CashRegisterService {

    private final CashRegisterRepository cashRegisterRepository;

    /**
     * this method outputs the current number of each denomination in the register
     * @param registerId
     * @return
     */
    public Register getCurrentBalance(int registerId) {
        Register register =  cashRegisterRepository.findById(Long.valueOf(registerId)).orElse(null);
        if(null != register) {
             return register;
        }
        return null;
    }

    /**
     * This method takes register ID and The new Cash denominations and puts them into the cash register by adding it to the current balance
     * @param registerId
     * @param newCash
     * @return
     */

    public Register updateTheCurrentBalance(int registerId, Map<CashType, CashValue> newCash, State state) {
        Register register =  cashRegisterRepository.findById(Long.valueOf(registerId)).orElse(null);
        if(null != register) {

            Map<CashType, CashValue> currentCashBalance = register.getCurrentCashBalance().getCashAmount();
            for (Map.Entry<CashType, CashValue> currentCashBalanceEntry : currentCashBalance.entrySet()) {
                double currentAmount;
                if(state.name().equals(State.PUT)) {
                    currentAmount = currentCashBalanceEntry.getValue().getAmount() + newCash.get(currentCashBalanceEntry.getKey()).getAmount();
                }else {
                    currentAmount = currentCashBalanceEntry.getValue().getAmount() - newCash.get(currentCashBalanceEntry.getKey()).getAmount();
                }

                currentCashBalanceEntry.getValue().setAmount(currentAmount);
            }

             return cashRegisterRepository.updateCurrentBalance(Long.valueOf(registerId), currentCashBalance);
        }
        return null;
    }

    public Register withdrawCashFromRegister(int registerId, Map<CashType, CashValue> cashToBeWithdrawn) {
        Register register =  cashRegisterRepository.findById(Long.valueOf(registerId)).orElse(null);
        if(null != register) {
            Map<CashType, CashValue> currentCashBalance = register.getCurrentCashBalance().getCashAmount();
            for (Map.Entry<CashType, CashValue> currentCashBalanceEntry : currentCashBalance.entrySet()) {
                double currentAmount = currentCashBalanceEntry.getValue().getAmount() - cashToBeWithdrawn.get(currentCashBalanceEntry.getKey()).getAmount();
                currentCashBalanceEntry.getValue().setAmount(currentAmount);
            }
        }
        return null;
    }





}
