package com.example.demo.data;

import com.example.demo.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    List<Client> findAll();

    @Query("Select c from Client c where c.EIK = ?1")
    Client findByEik(String eik);

    @Override
    void delete(Client entity);

    @Modifying
    @Query("update Client c set c = ?1 where c.EIK =?2")
    void updateClientByEik(Client client, String Eik);

    @Override
    <S extends Client> S save(S entity);
}
