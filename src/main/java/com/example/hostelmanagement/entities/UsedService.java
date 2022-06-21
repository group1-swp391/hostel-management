package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_UsedService", schema = "dbo", catalog = "Hostel_Management")
public class UsedService {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usedServiceID")
    private Integer usedServiceId;
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
    @Column(name = "servicetypeID")
    private Integer servicetypeId;
    @Basic
    @Column(name = "usedQuantity")
    private Integer usedQuantity;
    @Basic
    @Column(name = "price")
    private Double price;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;

    public UsedService(Integer roomId, Date startDate, Date endDate, Integer servicetypeId, Integer usedQuantity, Double price, Integer invoiceId) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.servicetypeId = servicetypeId;
        this.usedQuantity = usedQuantity;
        this.price = price;
        this.invoiceId = invoiceId;
    }

    public UsedService() {
    }

    public Integer getUsedServiceId() {
        return usedServiceId;
    }

    public void setUsedServiceId(Integer usedServiceId) {
        this.usedServiceId = usedServiceId;
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

    public Integer getServicetypeId() {
        return servicetypeId;
    }

    public void setServicetypeId(Integer servicetypeId) {
        this.servicetypeId = servicetypeId;
    }

    public Integer getUsedQuantity() {
        return usedQuantity;
    }

    public void setUsedQuantity(Integer usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
        return Objects.equals(usedServiceId, that.usedServiceId) && Objects.equals(roomId, that.roomId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(servicetypeId, that.servicetypeId) && Objects.equals(usedQuantity, that.usedQuantity) && Objects.equals(price, that.price) && Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedServiceId, roomId, startDate, endDate, servicetypeId, usedQuantity, price, invoiceId);
    }
}
