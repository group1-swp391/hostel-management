package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.UsedUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedUtilityRepository extends JpaRepository<UsedUtility, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM tbl_UsedUtility WHERE utilityTypeID IN (SELECT utilityTypeID FROM tbl_UtilityType WHERE utilityName LIKE :name)")
    List<UsedUtility> findAllByUtilityName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM tbl_UsedUtility WHERE roomID=:roomId AND utilityTypeID IN (SELECT utilityTypeID FROM tbl_UtilityType WHERE utilityName LIKE :name)")
    List<UsedUtility> findAllByRoomId(@Param("name") String name, @Param("roomId") int roomId);

    @Query(nativeQuery = true, value = "SELECT u.fullName FROM tbl_Users u, tbl_UsedUtility uti, tbl_Room r\n" +
            "WHERE uti.roomID = :roomId AND uti.roomID = r.roomID AND u.userID = r.UserID AND uti.utilityTypeID=:utilityTypeId")
    String getRenterNameByRoomId(@Param("roomId") int roomId, @Param("utilityTypeId") int utilityTypeId);

    List<UsedUtility> findAllByInvoiceIdNullAndRoomId(int roomId);

}