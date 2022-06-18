package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Invoice;
import com.example.hostelmanagement.models.UsedService;
import com.example.hostelmanagement.models.UsedUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedUtilityRepository extends JpaRepository<UsedUtility, Integer> {
    List<UsedUtility> findAllByInvoiceIdNullAndRoomId(int roomId);





}







