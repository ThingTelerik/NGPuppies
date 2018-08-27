package com.example.demo.controllers;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Subscriber;
import com.example.demo.services.base.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients/{clientID}")
public class BillController {

    private IBillService billService;
    @Autowired
    public BillController(IBillService billService){
        this.billService = billService;
    }

    @GetMapping("/{subscriberId}/bills")
    public Page<Bill> getAllUnpaidBills(@PathVariable(value = "subscriberId") Integer subscriberId,
                                                 Pageable pageable) {
        return billService.getAllUnpaidBills(subscriberId,pageable);
    }

    @GetMapping("/{subscriberId}/paidbills")
    public Page<Bill> getAllPaidBills(@PathVariable(value = "subscriberId") Integer subscriberId,
                                        Pageable pageable) {
        return billService.getAllPaidBills(subscriberId,pageable);
    }

}
