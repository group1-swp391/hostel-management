package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_UtilityType", schema = "dbo", catalog = "Hostel_Management")
public class TblUtilityType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "utilityTypeID")
    private Integer utilityTypeId;
    @Basic
    @Column(name = "utilityName")
    private String utilityName;
    @Basic
    @Column(name = "pricePerIndex")
    private Double pricePerIndex;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;

    public Integer getUtilityTypeId() {
        return utilityTypeId;
    }

    public void setUtilityTypeId(Integer utilityTypeId) {
        this.utilityTypeId = utilityTypeId;
    }

    public String getUtilityName() {
        return utilityName;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
    }

    public Double getPricePerIndex() {
        return pricePerIndex;
    }

    public void setPricePerIndex(Double pricePerIndex) {
        this.pricePerIndex = pricePerIndex;
    }

    public Integer getHostelId() {
        return hostelId;
    }

    public void setHostelId(Integer hostelId) {
        this.hostelId = hostelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblUtilityType that = (TblUtilityType) o;
        return Objects.equals(utilityTypeId, that.utilityTypeId) && Objects.equals(utilityName, that.utilityName) && Objects.equals(pricePerIndex, that.pricePerIndex) && Objects.equals(hostelId, that.hostelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilityTypeId, utilityName, pricePerIndex, hostelId);
    }
}
