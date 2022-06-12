package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "tbl_Hostel", schema = "dbo", catalog = "Hostel_Management")
public class Hostel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "hostelID")
    private int hostelId;
    @Basic
    @Column(name = "ownerHostelID")
    private int ownerHostelId;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "hostelName")
    private String hostelName;
    @Basic
    @Column(name = "room_quantity")
    private int roomQuantity;
    @Basic
    @Column(name = "hostelImg")
    private byte[] hostelImg;
    @Basic
    @Column(name = "hostelStatus")
    private boolean hostelStatus;

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public int getOwnerHostelId() {
        return ownerHostelId;
    }

    public void setOwnerHostelId(int ownerHostelId) {
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

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public byte[] getHostelImg() {
        return hostelImg;
    }

    public void setHostelImg(byte[] hostelImg) {
        this.hostelImg = hostelImg;
    }

    public boolean isHostelStatus() {
        return hostelStatus;
    }

    public void setHostelStatus(boolean hostelStatus) {
        this.hostelStatus = hostelStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hostel hostel = (Hostel) o;

        if (hostelId != hostel.hostelId) return false;
        if (ownerHostelId != hostel.ownerHostelId) return false;
        if (roomQuantity != hostel.roomQuantity) return false;
        if (hostelStatus != hostel.hostelStatus) return false;
        if (address != null ? !address.equals(hostel.address) : hostel.address != null) return false;
        if (hostelName != null ? !hostelName.equals(hostel.hostelName) : hostel.hostelName != null) return false;
        if (!Arrays.equals(hostelImg, hostel.hostelImg)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hostelId;
        result = 31 * result + ownerHostelId;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (hostelName != null ? hostelName.hashCode() : 0);
        result = 31 * result + roomQuantity;
        result = 31 * result + Arrays.hashCode(hostelImg);
        result = 31 * result + (hostelStatus ? 1 : 0);
        return result;
    }

    public Hostel() {
    }

    public Hostel(int ownerHostelId, String address, String hostelName, int roomQuantity, byte[] hostelImg, boolean hostelStatus) {
        this.ownerHostelId = ownerHostelId;
        this.address = address;
        this.hostelName = hostelName;
        this.roomQuantity = roomQuantity;
        this.hostelImg = hostelImg;
        this.hostelStatus = hostelStatus;
    }

    public Hostel(int hostelId, int ownerHostelId, String address, String hostelName, int roomQuantity, byte[] hostelImg, boolean hostelStatus) {
        this.hostelId = hostelId;
        this.ownerHostelId = ownerHostelId;
        this.address = address;
        this.hostelName = hostelName;
        this.roomQuantity = roomQuantity;
        this.hostelImg = hostelImg;
        this.hostelStatus = hostelStatus;
    }
}
