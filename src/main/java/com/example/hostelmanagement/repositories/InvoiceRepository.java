package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findAll();
    List<Invoice> findAllByInvoiceStatusIsTrue();



}







