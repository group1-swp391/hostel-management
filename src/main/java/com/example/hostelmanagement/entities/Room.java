package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.util.Arrays;

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
    private Integer userId;
    @Basic
    @Column(name = "typeID")
    private int typeId;
    @Basic
    @Column(name = "hostelID")
    private int hostelId;
    @Basic
    @Column(name = "image")
    private byte[] image;
    @Basic
    @Column(name = "roomStatus")
    private boolean roomStatus;

    @Transient
    private double price;
    @Transient
    private String roomName;
    @Transient
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

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

    public Integer getUserId() {
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

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(boolean roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (roomId != room.roomId) return false;
        if (roomNumber != room.roomNumber) return false;
        if (typeId != room.typeId) return false;
        if (hostelId != room.hostelId) return false;
        if (roomStatus != room.roomStatus) return false;
        if (userId != null ? !userId.equals(room.userId) : room.userId != null) return false;
        if (!Arrays.equals(image, room.image)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomId;
        result = 31 * result + roomNumber;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + hostelId;
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + (roomStatus ? 1 : 0);
        return result;
    }

    public Room() {
    }

    public Room(int roomNumber, Integer userId, int typeId, int hostelId, byte[] image, boolean roomStatus) {
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.typeId = typeId;
        this.hostelId = hostelId;
        this.image = image;
        this.roomStatus = roomStatus;
    }

    public Room(int roomId, int roomNumber, Integer userId, int typeId, int hostelId, byte[] image, boolean roomStatus) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.typeId = typeId;
        this.hostelId = hostelId;
        this.image = image;
        this.roomStatus = roomStatus;
    }
}
