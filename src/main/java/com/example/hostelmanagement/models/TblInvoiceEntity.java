package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_Invoice", schema = "dbo", catalog = "Hostel_Management")
public class TblInvoiceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invoiceID")
    private int invoiceId;
    @Basic
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "invoiceName")
    private String invoiceName;
    @Basic
    @Column(name = "totalAmount")
    private double totalAmount;
    @Basic
    @Column(name = "invoiceStatus")
    private boolean invoiceStatus;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "invoiceCreateDate")
    private Date invoiceCreateDate;
    @Basic
    @Column(name = "paymentDate")
    private Date paymentDate;
    public TblInvoiceEntity (){

    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(boolean invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getInvoiceCreateDate() {
        return invoiceCreateDate;
    }

    public void setInvoiceCreateDate(Date invoiceCreateDate) {
        this.invoiceCreateDate = invoiceCreateDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblInvoiceEntity that = (TblInvoiceEntity) o;
        return invoiceId == that.invoiceId && roomId == that.roomId && Double.compare(that.totalAmount, totalAmount) == 0 && invoiceStatus == that.invoiceStatus && Objects.equals(invoiceName, that.invoiceName) && Objects.equals(note, that.note) && Objects.equals(invoiceCreateDate, that.invoiceCreateDate) && Objects.equals(paymentDate, that.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, roomId, invoiceName, totalAmount, invoiceStatus, note, invoiceCreateDate, paymentDate);
    }
}
