package com.cfcook.cashregister.controller;

import com.cfcook.cashregister.service.CashRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("api/v1")
public class CashRegisterController {

    private final CashRegisterService cashRegisterService;
}
