package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Booking;
import com.example.hostelmanagement.models.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {






}
