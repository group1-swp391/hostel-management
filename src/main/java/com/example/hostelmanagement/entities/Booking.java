package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_Booking", schema = "dbo", catalog = "dlszowqj8t87ry7")
public class Booking {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bookingId")
    private Integer bookingId;
    @Basic
    @Column(name = "userID")
    private Integer userId;
    @Basic
    @Column(name = "roomID")
    private Integer roomId;
    @Basic
    @Column(name = "appointmentDate")
    private Date appointmentDate;

    public Booking() {
    }

    public Booking(Integer userId, Integer roomId, Date appointmentDate) {
        this.userId = userId;
        this.roomId = roomId;
        this.appointmentDate = appointmentDate;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(getBookingId(), booking.getBookingId()) && Objects.equals(getUserId(), booking.getUserId()) && Objects.equals(getRoomId(), booking.getRoomId()) && Objects.equals(getAppointmentDate(), booking.getAppointmentDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookingId(), getUserId(), getRoomId(), getAppointmentDate());
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", appointmentDate=" + appointmentDate +
                '}';
    }
}
