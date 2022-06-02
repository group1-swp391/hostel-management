package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Integer> {

    List<Hostel> getAllByHostelId(int x);
    List<Hostel> getAllByHostelStatusTrue();

}
