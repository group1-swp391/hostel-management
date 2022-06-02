package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HostelRepository extends JpaRepository<Hostel, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Hostel h WHERE h.ownerHostelID=:ownerHostelID AND h.hostelName LIKE %:hostelName% AND h.hostelStatus=1")
    List<Hostel> findAllByOwnerHostelID(@Param("hostelName") String hostelName,@Param("ownerHostelID") int ownerHostelID);
}
