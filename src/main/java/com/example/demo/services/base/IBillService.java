package com.example.demo.services.base;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Currency;
import com.example.demo.entities.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IBillService {

    List<Bill> TenMostResentPaidBillsForASubscriber(String bankName);

    Page<Bill> getAllUnpaidBills(Long clientId,Integer subscriberId, Pageable pageable);

    Page<Bill> getAllPaidBills(Long clientId,Integer subscriberId, Pageable pageable);

    Bill createUnpaidBill(Integer subscriberID, Bill newBill);

    Bill payBill(long id, Integer subscriberId, Long billId);

    List<Bill> payAllUnpaidBills(long id, Integer subscriberId);

    Bill createPureUnpaidBill(Integer subscriberID, Bill newBill) throws Exception;

    List<Bill> AllPaidBillsOrderedDesc(String bankName);
}
