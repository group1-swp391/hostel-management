package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_Invoice", schema = "dbo", catalog = "Hostel_Management")
public class Invoice {
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
    private Timestamp invoiceCreateDate;
    @Basic
    @Column(name = "paymentStatus")
    private boolean paymentStatus;
    @Basic
    @Column(name = "paymentDate")
    private Date paymentDate;

    public Invoice() {

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

    public Timestamp getInvoiceCreateDate() {
        return invoiceCreateDate;
    }

    public void setInvoiceCreateDate(Timestamp invoiceCreateDate) {
        this.invoiceCreateDate = invoiceCreateDate;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
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

        Invoice invoice = (Invoice) o;

        if (invoiceId != invoice.invoiceId) return false;
        if (roomId != invoice.roomId) return false;
        if (Double.compare(invoice.totalAmount, totalAmount) != 0) return false;
        if (invoiceStatus != invoice.invoiceStatus) return false;
        if (paymentStatus != invoice.paymentStatus) return false;
        if (invoiceName != null ? !invoiceName.equals(invoice.invoiceName) : invoice.invoiceName != null) return false;
        if (note != null ? !note.equals(invoice.note) : invoice.note != null) return false;
        if (invoiceCreateDate != null ? !invoiceCreateDate.equals(invoice.invoiceCreateDate) : invoice.invoiceCreateDate != null)
            return false;
        if (paymentDate != null ? !paymentDate.equals(invoice.paymentDate) : invoice.paymentDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = invoiceId;
        result = 31 * result + roomId;
        result = 31 * result + (invoiceName != null ? invoiceName.hashCode() : 0);
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (invoiceStatus ? 1 : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (invoiceCreateDate != null ? invoiceCreateDate.hashCode() : 0);
        result = 31 * result + (paymentStatus ? 1 : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        return result;
    }

    public Invoice(int roomId, String invoiceName, double totalAmount, boolean invoiceStatus, String note, Timestamp invoiceCreateDate, boolean paymentStatus, Date paymentDate) {
        this.roomId = roomId;
        this.invoiceName = invoiceName;
        this.totalAmount = totalAmount;
        this.invoiceStatus = invoiceStatus;
        this.note = note;
        this.invoiceCreateDate = invoiceCreateDate;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }
}
