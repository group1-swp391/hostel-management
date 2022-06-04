package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Integer> {
    List<Hostel> findAllByOwnerHostelIDAndHostelNameContainsAndHostelStatus(int ownerHostelID, String hostelName,boolean hostelStatus);
}
