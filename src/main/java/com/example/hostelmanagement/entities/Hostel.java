package com.example.hostelmanagement.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_Hostel")
public class Hostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hostelID;
    @Column(name = "ownerHostelID")
    private int ownerHostelID;
    @Column(name = "address")
    private String address;
    @Column(name = "hostelName")
    private String hostelName;
    @Column(name = "room_quantity")
    private int room_quality;
    @Column(name = "hostelStatus")
    private boolean hostelStatus;

    public Hostel(int ownerHostelID, String address, String hostelName, int room_quality, boolean hostelStatus) {
        this.ownerHostelID = ownerHostelID;
        this.address = address;
        this.hostelName = hostelName;
        this.room_quality = room_quality;
        this.hostelStatus = hostelStatus;
    }

    public Hostel(int hostelID, int ownerHostelID, String address, String hostelName, int room_quality, boolean hostelStatus) {
        this.hostelID = hostelID;
        this.ownerHostelID = ownerHostelID;
        this.address = address;
        this.hostelName = hostelName;
        this.room_quality = room_quality;
        this.hostelStatus = hostelStatus;
    }

    public Hostel() {
    }

    public int getHostelID() {
        return hostelID;
    }

    public void setHostelID(int hostelID) {
        this.hostelID = hostelID;
    }

    public int getOwnerHostelID() {
        return ownerHostelID;
    }

    public void setOwnerHostelID(int ownerHostelID) {
        this.ownerHostelID = ownerHostelID;
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

    public int getRoom_quality() {
        return room_quality;
    }

    public void setRoom_quality(int room_quality) {
        this.room_quality = room_quality;
    }

    public boolean isHostelStatus() {
        return hostelStatus;
    }

    public void setHostelStatus(boolean hostelStatus) {
        this.hostelStatus = hostelStatus;
    }

    @Override
    public String toString() {
        return "Hostel{" +
                "hostelID=" + hostelID +
                ", ownerHostelID=" + ownerHostelID +
                ", address='" + address + '\'' +
                ", hostelName='" + hostelName + '\'' +
                ", room_quality=" + room_quality +
                ", hostelStatus=" + hostelStatus +
                '}';
    }
}
