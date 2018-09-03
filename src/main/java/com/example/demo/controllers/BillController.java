package com.example.demo.controllers;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Currency;
import com.example.demo.entities.Services;
import com.example.demo.entities.Subscriber;
import com.example.demo.security.CurrentLoggedUser;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.services.base.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api/clients/")
public class BillController {

    private IBillService billService;
    @Autowired
    public BillController(IBillService billService){
        this.billService = billService;
    }


    /**
     * Get all unpaid bills by subscriber id that belongs to the current logged user
     * @param loggedUser
     * @param subscriberId
     * @param pageable
     * @return
     */
    @GetMapping("/{subscriberId}/bills")
    public Page<Bill> getAllUnpaidBills(@CurrentLoggedUser CustomUserDetails loggedUser, @PathVariable(value = "subscriberId") Integer subscriberId,
                                        Pageable pageable) {
        return billService.getAllUnpaidBills(loggedUser.getId(),subscriberId,pageable);
    }

    /**
     *      * Get all paid bills by subscriber id that belongs to the current logged user
     * @param loggedUser
     * @param subscriberId
     * @param pageable
     * @return
     */
    @GetMapping("/{subscriberId}/paidbills")
    public Page<Bill> getAllPaidBills(@CurrentLoggedUser CustomUserDetails loggedUser,@PathVariable(value = "subscriberId") Integer subscriberId,
                                      Pageable pageable) {
        return billService.getAllPaidBills(loggedUser.getId(),subscriberId,pageable);

    }

    /**
     * Pay bill by subscriber id that belongs to the current logged user
     * The date is set for the date when is the request send
     * @param loggedUser
     * @param subscriberId
     * @param billId
     * @return
     */
    @GetMapping("/{subscriberId}/payBill/{billId}")
    public Bill payBill(@CurrentLoggedUser CustomUserDetails loggedUser,
                        @PathVariable(value = "subscriberId") Integer subscriberId,
                        @PathVariable(value = "billId") Long billId){
        return this.billService.payBill(loggedUser.getId(),subscriberId,billId);

    }



    //http://localhost:8080/api/clients/lastTenBills?bankName=DSK ->works
    @GetMapping("/lastTenBills")
    public List<Bill> TenMostResentPaidBillsForASubscriber(@RequestParam(value = "bankName",required = true) String bankName){
        return billService.TenMostResentPaidBillsForASubscriber(bankName);
    }

    @PostMapping("/{subscriberId}/bills")
    public Bill createUnpaidBill(@PathVariable(value = "subscriberId") Integer subscriberID,@RequestBody Bill newBill){
        return billService.createUnpaidBill(subscriberID,newBill);
    }
}
