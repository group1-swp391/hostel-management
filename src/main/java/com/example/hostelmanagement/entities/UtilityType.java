package com.example.hostelmanagement.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_UtilityType", schema = "dbo", catalog = "Hostel_Management")
public class UtilityType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "utilityTypeID")
    private int utilityTypeId;
    @Basic
    @Column(name = "utilityName")
    private String utilityName;
    @Basic
    @Column(name = "pricePerIndex")
    private Double pricePerIndex;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;

    public int getUtilityTypeId() {
        return utilityTypeId;
    }

    public void setUtilityTypeId(int utilityTypeId) {
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

        UtilityType that = (UtilityType) o;

        if (utilityTypeId != that.utilityTypeId) return false;
        if (utilityName != null ? !utilityName.equals(that.utilityName) : that.utilityName != null) return false;
        if (pricePerIndex != null ? !pricePerIndex.equals(that.pricePerIndex) : that.pricePerIndex != null)
            return false;
        if (hostelId != null ? !hostelId.equals(that.hostelId) : that.hostelId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = utilityTypeId;
        result = 31 * result + (utilityName != null ? utilityName.hashCode() : 0);
        result = 31 * result + (pricePerIndex != null ? pricePerIndex.hashCode() : 0);
        result = 31 * result + (hostelId != null ? hostelId.hashCode() : 0);
        return result;
    }

    public UtilityType() {
    }

    public UtilityType(String utilityName, Double pricePerIndex, Integer hostelId) {
        this.utilityName = utilityName;
        this.pricePerIndex = pricePerIndex;
        this.hostelId = hostelId;
    }

    public UtilityType(int utilityTypeId, String utilityName, Double pricePerIndex, Integer hostelId) {
        this.utilityTypeId = utilityTypeId;
        this.utilityName = utilityName;
        this.pricePerIndex = pricePerIndex;
        this.hostelId = hostelId;
    }
}
