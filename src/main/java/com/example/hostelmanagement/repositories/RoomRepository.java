package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findAllByRoomNumber(int roomNumber);
}
