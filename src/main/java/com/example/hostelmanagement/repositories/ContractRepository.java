package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Integer> {

   List<Contracts> getAllByUserId(int userid);
   List<Contracts> findAllByContractStatusTrue();




}
