package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Integer> {

   List<Contracts> getAllByUserId(int userid);
   List<Contracts> findAllByContractStatusTrue();




}
