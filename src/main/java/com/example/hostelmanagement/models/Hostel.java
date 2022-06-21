package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_Hostel", schema = "dbo", catalog = "Hostel_Management7")
public class Hostel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "hostelID")
    private Integer hostelId;
    @Basic
    @Column(name = "ownerHostelID")
    private Integer ownerHostelId;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "hostelName")
    private String hostelName;
    @Basic
    @Column(name = "hostelStatus")
    private Boolean hostelStatus;
    @Basic
    @Column(name = "imageHostel")
    private String imageHostel;

    public Hostel() {
    }

    public Hostel(Integer ownerHostelId, String address, String hostelName, Boolean hostelStatus, String imageHostel) {
        this.ownerHostelId = ownerHostelId;
        this.address = address;
        this.hostelName = hostelName;
        this.hostelStatus = hostelStatus;
        this.imageHostel = imageHostel;
    }

    public Integer getHostelId() {
        return hostelId;
    }

    public void setHostelId(Integer hostelId) {
        this.hostelId = hostelId;
    }

    public Integer getOwnerHostelId() {
        return ownerHostelId;
    }

    public void setOwnerHostelId(Integer ownerHostelId) {
        this.ownerHostelId = ownerHostelId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public Boolean getHostelStatus() {
        return hostelStatus;
    }

    public void setHostelStatus(Boolean hostelStatus) {
        this.hostelStatus = hostelStatus;
    }

    public String getImageHostel() {
        return imageHostel;
    }

    public void setImageHostel(String imageHostel) {
        this.imageHostel = imageHostel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hostel hostel = (Hostel) o;
        return Objects.equals(hostelId, hostel.hostelId) && Objects.equals(ownerHostelId, hostel.ownerHostelId) && Objects.equals(address, hostel.address) && Objects.equals(hostelName, hostel.hostelName) && Objects.equals(hostelStatus, hostel.hostelStatus) && Objects.equals(imageHostel, hostel.imageHostel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostelId, ownerHostelId, address, hostelName, hostelStatus, imageHostel);
    }
}
