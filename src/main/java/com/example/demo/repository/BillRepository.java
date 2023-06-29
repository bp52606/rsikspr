package com.example.demo.repository;

import com.example.demo.dto.Bill;
import com.example.demo.dto.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository for bills.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    //Optional<Bill> findById(String sender);

}
