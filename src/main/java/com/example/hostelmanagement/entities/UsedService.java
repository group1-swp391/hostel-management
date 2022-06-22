package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_UsedService", schema = "dbo", catalog = "Hostel_Management")
public class UsedService {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usedServiceID")
    private int usedServiceId;
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
    @Column(name = "servicetypeID")
    private int servicetypeId;
    @Basic
    @Column(name = "usedQuantity")
    private int usedQuantity;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;

    public int getUsedServiceId() {
        return usedServiceId;
    }

    public void setUsedServiceId(int usedServiceId) {
        this.usedServiceId = usedServiceId;
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

    public int getServicetypeId() {
        return servicetypeId;
    }

    public void setServicetypeId(int servicetypeId) {
        this.servicetypeId = servicetypeId;
    }

    public int getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(int usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

        UsedService that = (UsedService) o;

        if (usedServiceId != that.usedServiceId) return false;
        if (roomId != that.roomId) return false;
        if (servicetypeId != that.servicetypeId) return false;
        if (usedQuantity != that.usedQuantity) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = usedServiceId;
        result = 31 * result + roomId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + servicetypeId;
        result = 31 * result + usedQuantity;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        return result;
    }
}
