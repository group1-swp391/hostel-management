package com.example.hostelmanagement.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_Room", schema = "dbo", catalog = "Hostel_Management")
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "roomNumber")
    private int roomNumber;
    @Basic
    @Column(name = "UserID")
    private int userId;
    @Basic
    @Column(name = "typeID")
    private int typeId;
    @Basic
    @Column(name = "roomStatus")
    private boolean roomStatus;
    @Basic
    @Column(name = "image")
    private byte[] image;
    @Basic
    @Column(name = "hostelID")
    private int hostelId;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(boolean roomStatus) {
        this.roomStatus = roomStatus;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(Integer hostelId) {
        this.hostelId = hostelId;
    }

    public Room() {
    }

    public Room(int roomId, int roomNumber, int userId, int typeId, boolean roomStatus, byte[] image, int hostelId) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.typeId = typeId;
        this.roomStatus = roomStatus;
        this.image = image;
        this.hostelId = hostelId;
    }

    public Room(int roomNumber, int userId, int typeId, boolean roomStatus, byte[] image, int hostelId) {
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.typeId = typeId;
        this.roomStatus = roomStatus;
        this.image = image;
        this.hostelId = hostelId;
    }
}
