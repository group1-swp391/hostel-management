package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Invoice;
import com.example.hostelmanagement.models.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {





}







