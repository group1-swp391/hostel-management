package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_roomCharge", schema = "dbo", catalog = "Hostel_Management")
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
    @Basic
    @Column(name = "price")
    private float price;

    public RoomCharge() {
    }

    public RoomCharge(Integer roomId, Date startDate, Date endDate, Integer invoiceId, float price) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.invoiceId = invoiceId;
        this.price = price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomCharge)) return false;
        RoomCharge that = (RoomCharge) o;
        return Float.compare(that.getPrice(), getPrice()) == 0 && Objects.equals(getRoomChargeId(), that.getRoomChargeId()) && Objects.equals(getRoomId(), that.getRoomId()) && Objects.equals(getStartDate(), that.getStartDate()) && Objects.equals(getEndDate(), that.getEndDate()) && Objects.equals(getInvoiceId(), that.getInvoiceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomChargeId(), getRoomId(), getStartDate(), getEndDate(), getInvoiceId(), getPrice());
    }

    @Override
    public String toString() {
        return "RoomCharge{" +
                "roomChargeId=" + roomChargeId +
                ", roomId=" + roomId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", invoiceId=" + invoiceId +
                ", price=" + price +
                '}';
    }
}
