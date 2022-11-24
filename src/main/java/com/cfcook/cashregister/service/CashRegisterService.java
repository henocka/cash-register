package com.cfcook.cashregister.service;

import com.cfcook.cashregister.dto.RegisterDto;
import com.cfcook.cashregister.entity.*;
import com.cfcook.cashregister.repository.CashRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CashRegisterService {

    private final CashRegisterRepository cashRegisterRepository;

    /**
     * this method outputs the current number of each denomination in the register
     *
     * @param registerId
     * @return
     */
    public ResponseEntity<RegisterDto> getCurrentBalance(int registerId) {
        Register register = cashRegisterRepository.findById(Long.valueOf(registerId)).orElse(null);
        if (null != register) {
            register.setState(State.GET);
        }
        return convertToRegisterDto(register);
    }

    /**
     * This method takes register ID and The new Cash denominations and puts them into the cash register by adding it to the current balance
     *
     * @param registerId
     * @param newCash
     * @return
     */

    public ResponseEntity<RegisterDto> updateTheCurrentBalance(int registerId, Map<CashType, CashValue> newCash, State state) {
        int totalBalance = 0;
        Register register = cashRegisterRepository.findById(Long.valueOf(registerId)).orElse(null);
        Map<CashType, CashValue> currentCashAmount = null != register ?
                register.getCurrentCashBalance().getCashAmount() :
                newCash;

        for (Map.Entry<CashType, CashValue> currentCashBalanceEntry : currentCashAmount.entrySet()) {
            int currentAmount;
            if (state.name().equals(State.PUT.name())) {
                currentAmount = currentCashBalanceEntry.getValue().getAmount() + newCash.get(currentCashBalanceEntry.getKey()).getAmount();
            } else {
                currentAmount = currentCashBalanceEntry.getValue().getAmount() - newCash.get(currentCashBalanceEntry.getKey()).getAmount();
            }
            totalBalance += currentAmount;
            currentCashBalanceEntry.getValue().setAmount(currentAmount);
        }

        if (null != register) {
            register.setState(state);
            register.setCurrentCashBalance(
                    Cash.builder()
                            .id(register.getCurrentCashBalance().getId())
                            .cashAmount(currentCashAmount)
                            .build());
            register.setTotalValue(totalBalance);
            return convertToRegisterDto(cashRegisterRepository.save(register));
        } else {
            return convertToRegisterDto(cashRegisterRepository.save(Register.builder()
                    .state(state)
                    .currentCashBalance(Cash.builder().cashAmount(newCash).build())
                    .totalValue(totalBalance)
                    .build()));
        }

    }

    public ResponseEntity<RegisterDto> getChange(int registerId, int change) {
        int noOfTwenties, noOfTens, noOfFives, noOfTwos, noOfOnes, sum;
        noOfTwenties =  noOfTens = noOfFives = noOfTwos = noOfOnes = sum = 0;
        RegisterDto register = getCurrentBalance(registerId).getBody();
        int availableTwenties = register.getCurrentCashBalance().get(CashType.$20s);
        int availableTens = register.getCurrentCashBalance().get(CashType.$10s);
        int availableFives = register.getCurrentCashBalance().get(CashType.$5s);
        int availableTwos = register.getCurrentCashBalance().get(CashType.$2s);
        int availableOnes = register.getCurrentCashBalance().get(CashType.$1s);
        int totalCashOnHand = register.getCurrentTotalSum();
        if (totalCashOnHand < change) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        while (sum < change) {
            if (availableTwenties != 0 && (sum + 20) < change) {
                sum = sum + 20;
                availableTwenties = availableTwenties - 1;
                noOfTwenties = noOfTwenties + 1;
            } else if (availableTens != 0 && (sum + 10) < change) {
                sum = sum + 10;
                availableTens = availableTens - 1;
                noOfTens = noOfTens + 1;
            } else if (availableFives != 0 && (sum + 5) < change) {
                sum = sum + 5;
                availableFives = availableFives - 1;
                noOfFives = noOfFives + 1;
            } else if (availableTwos != 0 && (sum + 2) < change) {
                sum = sum + 2;
                availableTwos = availableTwos - 1;
                noOfTwos = noOfTwos + 1;
            } else if (availableOnes != 0 && (sum + 1) < change) {
                sum = sum + 1;
                availableOnes = availableOnes - 1;
                noOfOnes = noOfOnes + 1;
            } else {
                break;
            }

        }

        if (sum != change) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        Map<CashType, Integer> changeMap = new HashMap<>();
        changeMap.put(CashType.$20s, noOfTwenties);
        changeMap.put(CashType.$10s, noOfTens);
        changeMap.put(CashType.$5s, noOfFives);
        changeMap.put(CashType.$2s, noOfTwos);
        changeMap.put(CashType.$1s, noOfOnes);
        RegisterDto registerDto = RegisterDto.builder()
                .currentState(State.CHANGE)
                .currentCashBalance(changeMap)
                .currentTotalSum(change)
                .build();
        return new ResponseEntity<>(registerDto, HttpStatus.OK);

    }


    public ResponseEntity<RegisterDto> convertToRegisterDto(Register registerResult) {
        Map<CashType, Integer> currentCashBalance = new HashMap<>();

        for (Map.Entry<CashType, CashValue> currentCashBalanceEntry : registerResult.getCurrentCashBalance().getCashAmount().entrySet()) {
            currentCashBalance.put(currentCashBalanceEntry.getKey(), currentCashBalanceEntry.getValue().getAmount());
        }
        RegisterDto registerDto = RegisterDto.builder()
                .registerId(registerResult.getId())
                .registerName(registerResult.getName())
                .currentState(registerResult.getState())
                .currentCashBalance(currentCashBalance)
                .currentTotalSum(registerResult.getTotalValue())
                .build();
        return new ResponseEntity<>(registerDto, HttpStatus.OK);
    }
}
