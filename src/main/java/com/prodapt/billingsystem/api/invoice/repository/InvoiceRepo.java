package com.prodapt.billingsystem.api.invoice.repository;

import com.prodapt.billingsystem.api.invoice.dto.InvoiceResponseDTO;
import com.prodapt.billingsystem.api.invoice.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long>{
    @Query(value = "select user.id as userId, sub.id as subsID, SUM(plans.price) as amount, COUNT(*) as noOfPlans from user LEFT JOIN subscription_details as sub ON user.id = sub.user_id LEFT JOIN plans ON plans.id=sub.plan_id where user.id=:id GROUP BY user.id", nativeQuery = true)
    public Optional<InvoiceResponseDTO> generateInvoice(@Param("id") Long userId);

    List<Invoice> findAllByUserId(Long userId);

    Optional<Invoice> findByUuid(UUID uuid);

    int countAllByStatus(String status);
 }
