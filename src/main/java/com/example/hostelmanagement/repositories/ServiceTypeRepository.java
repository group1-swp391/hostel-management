package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {
    List<ServiceType> findAllByServiceNameContains(String serviceName);
}
