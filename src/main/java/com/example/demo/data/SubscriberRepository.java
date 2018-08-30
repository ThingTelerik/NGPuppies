package com.example.demo.data;

import com.example.demo.entities.Client;
import com.example.demo.entities.Services;
import com.example.demo.entities.Subscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

    Page<Subscriber> findAllByBank_Id(Long clientID,Pageable pageble);
    Subscriber findByBank_IdAndId(Long clientId,Integer subscriberID);

}
