package com.cfcook.cashregister.controller;

import com.cfcook.cashregister.dto.ChangeRequest;
import com.cfcook.cashregister.dto.ChangeResponse;
import com.cfcook.cashregister.dto.RegisterDto;
import com.cfcook.cashregister.dto.RegisterRequest;
import com.cfcook.cashregister.entity.CashType;
import com.cfcook.cashregister.entity.CashValue;
import com.cfcook.cashregister.entity.Register;
import com.cfcook.cashregister.entity.State;
import com.cfcook.cashregister.service.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("api/v1")
public class CashRegisterController {

    private final CashRegisterService cashRegisterService;

    @PostMapping ("/put")
    public ResponseEntity<RegisterDto> updateTheCurrentBalance(@RequestParam int register_id, @RequestBody RegisterRequest registerRequestEntity) {
        Map<CashType, CashValue> cash = new HashMap<>();
        for (Map.Entry<String, Integer> currentCash : registerRequestEntity.getCashAmount().entrySet()) {
            cash.put(CashType.valueOf(currentCash.getKey()), CashValue.builder().cashType(CashType.valueOf(currentCash.getKey())).amount(currentCash.getValue()).build());
        }
        return cashRegisterService.updateTheCurrentBalance(register_id, cash, registerRequestEntity.getState());
    }


    @PostMapping ("/change")
    public ResponseEntity<ChangeResponse> getChange(@RequestParam int register_id, @RequestBody ChangeRequest changeRequestEntity) {
        return  cashRegisterService.getChange( register_id,  changeRequestEntity.getChange());

    }

    @GetMapping("/get")
    public ResponseEntity<RegisterDto>  getCurrentBalance(@RequestParam int register_id) {
        return   cashRegisterService.getCurrentBalance(register_id);
    }


//    @GetMapping("/put")
//    public void updateTheCurrentBalance( ) {
//        Map<CashType, CashValue> cashs = new HashMap<>();
//        cashs.put(CashType.$20s, CashValue.builder().cashType(CashType.$20s).amount(30).build());
//        cashs.put(CashType.$10s, CashValue.builder().cashType(CashType.$10s).amount(20).build());
//        cashs.put(CashType.$5s, CashValue.builder().cashType(CashType.$5s).amount(90).build());
//        cashs.put(CashType.$2s, CashValue.builder().cashType(CashType.$2s).amount(60).build());
//        cashs.put(CashType.$1s, CashValue.builder().cashType(CashType.$1s).amount(10).build());
//
//        cashRegisterService.updateTheCurrentBalance(1, cashs, State.PUT);
//    }


//    @GetMapping("/get")
//    public Register getCurrentBalance() {
//        return cashRegisterService.getCurrentBalanceOld(1);
//    }


}
