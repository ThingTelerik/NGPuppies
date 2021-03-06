package com.example.demo.data;

import com.example.demo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    List<Client> findAll();

    @Query("Select c from Client c where c.id = ?1")
    Client findClientById(Long aLong);

     Client findByUsername(String Username);

    @Query("Select c from Client c where c.username = ?1")
    Client findClientByUsername(String username);

    @Query("Select c from Client c where c.EIK = ?1")
    Client findByEik(String eik);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Client c WHERE c.EIK = ?1")
    Boolean existsByEik(String eik);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Client c WHERE c.username = ?1")
    Boolean existsByUsername(String username);


    @Override
    void delete(Client entity);

    @Modifying
    @Query("update Client c set c.EIK = ?1 where c.username =?2")
    Client updateClientByEik(Client Eik, String username);

    @Modifying
    @Query("Delete from Client c where c.username = ?1")
    void deleteUserByUsername(String username);



}
