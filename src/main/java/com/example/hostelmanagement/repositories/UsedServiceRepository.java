package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Invoice;
import com.example.hostelmanagement.models.UsedService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedServiceRepository extends JpaRepository<UsedService, Integer> {
    List<UsedService> findAllByInvoiceIdNullAndRoomId(int roomid);



}







