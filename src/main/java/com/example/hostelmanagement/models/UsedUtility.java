package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_UsedUtility", schema = "dbo", catalog = "Hostel_Management7")
public class UsedUtility {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usedUtilityID")
    private Integer usedUtilityId;
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
    @Column(name = "utilityTypeID")
    private Integer utilityTypeId;
    @Basic
    @Column(name = "oldIndex")
    private Integer oldIndex;
    @Basic
    @Column(name = "newIndex")
    private Integer newIndex;
    @Basic
    @Column(name = "pricePerIndex")
    private Double pricePerIndex;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;

    public UsedUtility(Integer roomId, Date startDate, Date endDate, Integer utilityTypeId, Integer oldIndex, Integer newIndex, Double pricePerIndex, Integer invoiceId) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.utilityTypeId = utilityTypeId;
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.pricePerIndex = pricePerIndex;
        this.invoiceId = invoiceId;
    }

    public UsedUtility() {
    }

    public Integer getUsedUtilityId() {
        return usedUtilityId;
    }

    public void setUsedUtilityId(Integer usedUtilityId) {
        this.usedUtilityId = usedUtilityId;
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

    public Integer getUtilityTypeId() {
        return utilityTypeId;
    }

    public void setUtilityTypeId(Integer utilityTypeId) {
        this.utilityTypeId = utilityTypeId;
    }

    public Integer getOldIndex() {
        return oldIndex;
    }

    public void setOldIndex(Integer oldIndex) {
        this.oldIndex = oldIndex;
    }

    public Integer getNewIndex() {
        return newIndex;
    }

    public void setNewIndex(Integer newIndex) {
        this.newIndex = newIndex;
    }

    public Double getPricePerIndex() {
        return pricePerIndex;
    }

    public void setPricePerIndex(Double pricePerIndex) {
        this.pricePerIndex = pricePerIndex;
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
        UsedUtility that = (UsedUtility) o;
        return Objects.equals(usedUtilityId, that.usedUtilityId) && Objects.equals(roomId, that.roomId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(utilityTypeId, that.utilityTypeId) && Objects.equals(oldIndex, that.oldIndex) && Objects.equals(newIndex, that.newIndex) && Objects.equals(pricePerIndex, that.pricePerIndex) && Objects.equals(invoiceId, that.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedUtilityId, roomId, startDate, endDate, utilityTypeId, oldIndex, newIndex, pricePerIndex, invoiceId);
    }
}
