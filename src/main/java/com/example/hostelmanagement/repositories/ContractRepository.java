package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Integer> {
    List<Contract> findAllByContractByUserID(int userID);
    Contract findContractByContractIDAndUserID(int contractID, int userID);
}
