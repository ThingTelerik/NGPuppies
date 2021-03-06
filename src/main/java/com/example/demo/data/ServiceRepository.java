package com.example.demo.data;

import com.example.demo.entities.Services;
import com.example.demo.model.ServicesDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ServiceRepository extends JpaRepository<Services, Long> {

    Services getById(Long ServiceId);


    Services findByName(String name);

    Optional<Services> getByName(String serviceName);

    @Query(value = "SELECT * FROM usersdemo.services\n" +
            "join bills on bills.service_id = services.id \n" +
            "join subscribers on bills.subscriber_id = subscribers.id\n" +
            "where subscribers.phone_number = ?1 and bills.payment_date is not null;", nativeQuery = true)
    List<Services> allPaidServicesBySubscriber(String phone);


    @Query("Select s from Services s join Subscriber sub where sub.phoneNumber = ?1")
    List<Services> findBySubscriberPhone(String phone);

    @Query("Select s from Services s join Subscriber sub where sub.EGN = ?1")
    List<Services> findBySubscriberEGN(String egn);

    @Modifying
    @Query("update Services s set s = ?1 where s.name =?2")
    Services updateServiceByName(Services services, String name);


}
