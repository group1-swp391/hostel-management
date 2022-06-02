package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_roomCharge", schema = "dbo", catalog = "Hostel_Management7")
public class RoomCharge {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roomChargeID")
    private Integer roomChargeId;
    @Basic
    @Column(name = "roomID")
    private Integer roomId;
    @Basic
    @Column(name = "startDate")
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    private Date endDate;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;

    public RoomCharge() {
    }

    public RoomCharge(Integer roomId, Date startDate, Date endDate, Integer invoiceId) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.invoiceId = invoiceId;
    }

    public Integer getRoomChargeId() {
        return roomChargeId;
    }

    public void setRoomChargeId(Integer roomChargeId) {
        this.roomChargeId = roomChargeId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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
        RoomCharge that = (RoomCharge) o;
        return Objects.equals(roomChargeId, that.roomChargeId) && Objects.equals(roomId, that.roomId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomChargeId, roomId, startDate, endDate, invoiceId);
    }
}
