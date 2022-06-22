package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_Booking", schema = "dbo", catalog = "Hostel_Management")
public class Booking {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bookingId")
    private int bookingId;
    @Basic
    @Column(name = "userID")
    private Integer userId;
    @Basic
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "appointmentDate")
    private Date appointmentDate;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "phone")
    private String phone;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (bookingId != booking.bookingId) return false;
        if (roomId != booking.roomId) return false;
        if (userId != null ? !userId.equals(booking.userId) : booking.userId != null) return false;
        if (appointmentDate != null ? !appointmentDate.equals(booking.appointmentDate) : booking.appointmentDate != null)
            return false;
        if (email != null ? !email.equals(booking.email) : booking.email != null) return false;
        if (phone != null ? !phone.equals(booking.phone) : booking.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookingId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + roomId;
        result = 31 * result + (appointmentDate != null ? appointmentDate.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    public Booking(Integer userId, int roomId, Date appointmentDate, String email, String phone) {
        this.userId = userId;
        this.roomId = roomId;
        this.appointmentDate = appointmentDate;
        this.email = email;
        this.phone = phone;
    }
}
