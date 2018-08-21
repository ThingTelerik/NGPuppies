package com.example.demo.data;

import com.example.demo.entities.Bill;
import com.example.demo.entities.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Override
    List<Bill> findAllById(Iterable<Long> longs);

    @Override
    List<Bill> findAll();

    @Query("Select bill from bills where bill.subscriber.id = :subscriberId")
     List<Bill> findBySubscriber(@Param("subscriberId")  Long subscriberId);
}
