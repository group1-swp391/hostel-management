package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.UtilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilityTypeRepository extends JpaRepository<UtilityType, Integer> {
    List<UtilityType> findAllByUtilityNameContains(String utilityName);
}