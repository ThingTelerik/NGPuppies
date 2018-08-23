//package com.example.demo.data;
//
//import com.example.demo.entities.Bill;
//import com.example.demo.entities.Subscriber;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface BillRepository extends JpaRepository<Bill, Long> {
//
//    @Override
//    List<Bill> findAllById(Iterable<Long> longs);
//
//    @Override
//    List<Bill> findAll();
//
//   // @Query("Select b from bills b where b.subscriber.id = :subscriberId")
//    // List<Bill> findBySubscriber(@Param("subscriberId")  Long subscriberId);
//}
