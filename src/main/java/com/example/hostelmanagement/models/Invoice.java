package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_Invoice", schema = "dbo", catalog = "Hostel_Management7")
public class Invoice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invoiceID")
    private Integer invoiceId;
    @Basic
    @Column(name = "roomID")
    private Integer roomId;
    @Basic
    @Column(name = "invoiceName")
    private String invoiceName;
    @Basic
    @Column(name = "totalAmount")
    private Double totalAmount;
    @Basic
    @Column(name = "invoiceStatus")
    private Boolean invoiceStatus;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "invoiceCreateDate")
    private Date invoiceCreateDate;
    @Basic
    @Column(name = "paymentDate")
    private Date paymentDate;

    public Invoice() {
    }

    public Invoice(Integer roomId, String invoiceName, Double totalAmount, Boolean invoiceStatus, String note, Date invoiceCreateDate, Date paymentDate) {
        this.roomId = roomId;
        this.invoiceName = invoiceName;
        this.totalAmount = totalAmount;
        this.invoiceStatus = invoiceStatus;
        this.note = note;
        this.invoiceCreateDate = invoiceCreateDate;
        this.paymentDate = paymentDate;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Boolean invoiceStatus) {
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
        Invoice that = (Invoice) o;
        return Objects.equals(invoiceId, that.invoiceId) && Objects.equals(roomId, that.roomId) && Objects.equals(invoiceName, that.invoiceName) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(invoiceStatus, that.invoiceStatus) && Objects.equals(note, that.note) && Objects.equals(invoiceCreateDate, that.invoiceCreateDate) && Objects.equals(paymentDate, that.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, roomId, invoiceName, totalAmount, invoiceStatus, note, invoiceCreateDate, paymentDate);
    }
}
