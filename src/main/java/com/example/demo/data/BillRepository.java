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
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "SELECT * FROM bill \n" +
            "JOIN subscribers ON bill.subscriber_id= subscribers.id\n" +
            "JOIN users ON users.id = subscribers.bank_id\n" +
            "WHERE users.username = ?1\n" +
            "AND bill.payment_date IS NOT NULL\n" +
            "ORDER BY bill.payment_date DESC\n" +
            "LIMIT 10;", nativeQuery = true)
    List<Bill> TenMostResentPaidBills(String BankName);

    Page<Bill> findAllBySubscriber_IdAndPaymentDateIsNull(Integer subscriberID, Pageable pageble);
    List<Bill> findAllBySubscriber_IdAndPaymentDateIsNull(Integer subscriberID);

    Page<Bill> findAllBySubscriber_IdAndPaymentDateIsNotNull(Integer subscriberID,Pageable pageable);

    List<Bill> findAllBySubscriber_IdAndPaymentDateIsAfterAndPaymentDateIsBefore(Integer subscriberId, LocalDate startDate, LocalDate endDate);
    //List<Bill> findAllBySubscriber_IdAndService_NameAAndPaymentDateIsNotNull(Integer subscriberID,String serviceName, LocalDate payDate);

    Optional<Bill> findBySubscriber_IdAndIdAndPaymentDateIsNull(Integer subscriberId,Long billId);

    @Query(value = "SELECT * FROM bill \n" +
            "JOIN subscribers ON bill.subscriber_id= subscribers.id\n" +
            "JOIN users ON users.id = subscribers.bank_id\n" +
            "WHERE users.username = ?1\n" +
            "AND bill.payment_date IS NOT NULL\n" +
            "ORDER BY bill.payment_date DESC\n" , nativeQuery = true)
    List<Bill> findAllPaidBillsOrderedDesc(String BankName);
}
