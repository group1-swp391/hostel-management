package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomChargeRepository extends JpaRepository<Invoice, Integer> {





}







