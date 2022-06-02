package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    List<RoomType> getTblRoomTypeByRoomTypeStatusTrue();
    List<RoomType> getTblRoomTypeByRoomTypeStatusTrueAndHostelId(int x);

}
