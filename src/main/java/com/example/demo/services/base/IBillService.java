package com.example.demo.services.base;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IBillService {



    Page<Bill> getAllUnpaidBills(Integer subscriberId, Pageable pageable);

    Page<Bill> getAllPaidBills(Integer subscriberId, LocalDate payDate, Pageable pageable);

    Bill createUnpaidBill(Integer subscriberID, Services services, Bill newBill);

}
