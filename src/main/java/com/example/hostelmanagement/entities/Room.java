package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "tbl_Room", schema = "dbo", catalog = "dlszowqj8t87ry7")
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

    @ManyToOne
    @JoinColumn(name = "typeID", referencedColumnName = "typeID",  insertable = false, updatable = false)
    private RoomType roomType;

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getHostelId() {
        return getRoomType().getHostelId();
    }

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


}
