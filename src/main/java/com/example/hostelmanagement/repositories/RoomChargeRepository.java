package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.RoomCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomChargeRepository extends JpaRepository<RoomCharge, Integer> {

    List<RoomCharge> getAllByRoomIdAndInvoiceIdNull (int roomId);






}







