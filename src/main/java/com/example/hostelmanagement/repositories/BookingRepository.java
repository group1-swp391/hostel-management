package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.Booking;
import com.example.hostelmanagement.entities.UsedUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByRoomId(int roomID);
    List<Booking> findAll();
    @Query(nativeQuery = true,value = "Select b.bookingid,b.userid,b.roomid,b.appointmentDate,b.startdate,b.enddate,b.isbookingaccept,b.depositpaymentstatus,b.invoiceid From tbl_Booking b join tbl_Room r on b.roomID = r.roomID where r.hostelID =:hostelID")
    List<Booking> findAllBookingByHostelID(@Param("hostelID") int hostelID);






}
