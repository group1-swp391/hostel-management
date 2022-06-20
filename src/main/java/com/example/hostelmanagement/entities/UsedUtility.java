package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_UsedUtility", schema = "dbo", catalog = "Hostel_Management")
public class UsedUtility {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usedUtilityID")
    private int usedUtilityId;
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
    @Column(name = "utilityTypeID")
    private int utilityTypeId;
    @Basic
    @Column(name = "oldIndex")
    private int oldIndex;
    @Basic
    @Column(name = "newIndex")
    private int newIndex;
    @Basic
    @Column(name = "pricePerIndex")
    private double pricePerIndex;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;

    @Transient
    private String renterName;

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public int getUsedUtilityId() {
        return usedUtilityId;
    }

    public void setUsedUtilityId(int usedUtilityId) {
        this.usedUtilityId = usedUtilityId;
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

    public int getUtilityTypeId() {
        return utilityTypeId;
    }

    public void setUtilityTypeId(int utilityTypeId) {
        this.utilityTypeId = utilityTypeId;
    }

    public int getOldIndex() {
        return oldIndex;
    }

    public void setOldIndex(int oldIndex) {
        this.oldIndex = oldIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public void setNewIndex(int newIndex) {
        this.newIndex = newIndex;
    }

    public double getPricePerIndex() {
        return pricePerIndex;
    }

    public void setPricePerIndex(double pricePerIndex) {
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

        if (usedUtilityId != that.usedUtilityId) return false;
        if (roomId != that.roomId) return false;
        if (utilityTypeId != that.utilityTypeId) return false;
        if (oldIndex != that.oldIndex) return false;
        if (newIndex != that.newIndex) return false;
        if (Double.compare(that.pricePerIndex, pricePerIndex) != 0) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = usedUtilityId;
        result = 31 * result + roomId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + utilityTypeId;
        result = 31 * result + oldIndex;
        result = 31 * result + newIndex;
        temp = Double.doubleToLongBits(pricePerIndex);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        return result;
    }
}
