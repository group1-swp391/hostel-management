package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_Booking", schema = "dbo", catalog = "Hostel_Management7")
public class TblBooking {
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
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;

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

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblBooking that = (TblBooking) o;
        return Objects.equals(bookingId, that.bookingId) && Objects.equals(userId, that.userId) && Objects.equals(roomId, that.roomId) && Objects.equals(appointmentDate, that.appointmentDate) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(isBookingAccecpt, that.isBookingAccecpt) && Objects.equals(bookingStatus, that.bookingStatus) && Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, userId, roomId, appointmentDate, startDate, endDate, isBookingAccecpt, bookingStatus, invoiceId);
    }
}
