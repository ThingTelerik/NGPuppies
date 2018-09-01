package com.example.demo.controllers;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Services;
import com.example.demo.entities.Subscriber;
import com.example.demo.services.base.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class BillController {

    private IBillService billService;
    @Autowired
    public BillController(IBillService billService){
        this.billService = billService;
    }

    @GetMapping("/{clientID}/{subscriberId}/bills")
    public Page<Bill> getAllUnpaidBills(@PathVariable(value = "subscriberId") Integer subscriberId,
                                                 Pageable pageable) {
        return billService.getAllUnpaidBills(subscriberId,pageable);
    }

//TODO fix, not tested, bill added for billRepo method compile
    @GetMapping("/{clientID}/{subscriberId}/paidbills")
    public Page<Bill> getAllPaidBills(@PathVariable(value = "subscriberId")  Integer subscriberId, Bill bill, Pageable pageable) {
        return billService.getAllPaidBills(subscriberId,bill.getPaymentDate(),pageable);
    }

    //http://localhost:8080/api/clients/lastTenBills?bankName=DSK ->works
    @GetMapping("/lastTenBills")
    public List<Bill> TenMostResentPaidBillsForASubscriber(@RequestParam(value = "bankName",required = true) String bankName){
        return billService.TenMostResentPaidBillsForASubscriber(bankName);
    }

//    @PostMapping("/{subscriberId}/bills")
//    public Bill createUnpaidBill(@PathVariable(value = "subscriberId") Integer subscriberID, @RequestBody Services billService, @RequestBody Bill newBill){
//        return billService.createUnpaidBill(subscriberID,billService,newBill);
//    }
}
