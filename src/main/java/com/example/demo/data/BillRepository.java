package com.example.demo.data;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Subscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    Page<Bill> findAllBySubscriber_IdAndPaymentDateIsNull(Integer subscriberID,Pageable pageble);
    Page<Bill> findAllBySubscriber_IdAndPaymentDateIsNotNull(Integer subscriberID,Pageable pageable);

    List<Bill> findAllBySubscriber_IdAndPaymentDateIsAfterAndPaymentDateIsBefore(Integer subscriberId,LocalDate startDate, LocalDate endDate);
    List<Bill> findAllBySubscriber_IdAndService_NameAAndPaymentDateIsNotNull();

}
