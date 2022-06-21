package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_Booking", schema = "dbo", catalog = "Hostel_Management")
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
    @Basic
    @Column(name = "startDate")
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    private Date endDate;
    @Basic
    @Column(name = "isBookingAccecpt")
    private Boolean isBookingAccecpt;
    @Basic
    @Column(name = "bookingStatus")
    private Boolean bookingStatus;


    public Booking() {
    }

    public Booking(Integer userId, Integer roomId, Date appointmentDate, Date startDate, Date endDate, Boolean isBookingAccecpt, Boolean bookingStatus) {
        this.userId = userId;
        this.roomId = roomId;
        this.appointmentDate = appointmentDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isBookingAccecpt = isBookingAccecpt;
        this.bookingStatus = bookingStatus;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getBookingAccecpt() {
        return isBookingAccecpt;
    }

    public void setBookingAccecpt(Boolean bookingAccecpt) {
        isBookingAccecpt = bookingAccecpt;
    }

    public Boolean getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Boolean bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(getBookingId(), booking.getBookingId()) && Objects.equals(getUserId(), booking.getUserId()) && Objects.equals(getRoomId(), booking.getRoomId()) && Objects.equals(getAppointmentDate(), booking.getAppointmentDate()) && Objects.equals(getStartDate(), booking.getStartDate()) && Objects.equals(getEndDate(), booking.getEndDate()) && Objects.equals(isBookingAccecpt, booking.isBookingAccecpt) && Objects.equals(getBookingStatus(), booking.getBookingStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookingId(), getUserId(), getRoomId(), getAppointmentDate(), getStartDate(), getEndDate(), isBookingAccecpt, getBookingStatus());
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", appointmentDate=" + appointmentDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isBookingAccecpt=" + isBookingAccecpt +
                ", bookingStatus=" + bookingStatus +
                '}';
    }
}
