package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_roomCharge", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class RoomCharge {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roomChargeID")
    private int roomChargeId;
    @Basic
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "startDate")
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    private Date endDate;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "invoiceID")
    private int invoiceId;
    @ManyToOne
    @JoinColumn(name = "roomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room roomByRoomId;
    @ManyToOne
    @JoinColumn(name = "invoiceID", referencedColumnName = "invoiceID", insertable = false, updatable = false)
    private Invoice invoiceByInvoiceId;

    public int getRoomChargeId() {
        return roomChargeId;
    }

    public void setRoomChargeId(int roomChargeId) {
        this.roomChargeId = roomChargeId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomCharge that = (RoomCharge) o;

        if (roomChargeId != that.roomChargeId) return false;
        if (roomId != that.roomId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (invoiceId != that.invoiceId) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = roomChargeId;
        result = 31 * result + roomId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + invoiceId;
        return result;
    }

    public Room getTblRoomByRoomId() {
        return roomByRoomId;
    }

    public void setTblRoomByRoomId(Room roomByRoomId) {
        this.roomByRoomId = roomByRoomId;
    }

    public Invoice getTblInvoiceByInvoiceId() {
        return invoiceByInvoiceId;
    }

    public void setTblInvoiceByInvoiceId(Invoice invoiceByInvoiceId) {
        this.invoiceByInvoiceId = invoiceByInvoiceId;
    }
}
