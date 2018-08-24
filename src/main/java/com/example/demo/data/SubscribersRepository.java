package com.example.demo.data;

import com.example.demo.entities.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribersRepository extends JpaRepository<Subscriber, Integer> {
}