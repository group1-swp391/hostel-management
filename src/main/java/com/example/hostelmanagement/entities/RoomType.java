package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "tbl_RoomType", schema = "dbo", catalog = "Hostel_Management")
public class RoomType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "typeID")
    private int typeId;
    @Basic
    @Column(name = "hostelID")
    private int hostelId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "depositPrice")
    private double depositPrice;
    @Basic
    @Column(name = "roomTImg")
    private byte[] roomTImg;
    @Basic
    @Column(name = "roomName")
    private String roomName;
    @Basic
    @Column(name = "roomTypeStatus")
    private boolean roomTypeStatus;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public byte[] getRoomTImg() {
        return roomTImg;
    }

    public void setRoomTImg(byte[] roomTImg) {
        this.roomTImg = roomTImg;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isRoomTypeStatus() {
        return roomTypeStatus;
    }

    public void setRoomTypeStatus(boolean roomTypeStatus) {
        this.roomTypeStatus = roomTypeStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomType roomType = (RoomType) o;

        if (typeId != roomType.typeId) return false;
        if (hostelId != roomType.hostelId) return false;
        if (Double.compare(roomType.price, price) != 0) return false;
        if (Double.compare(roomType.depositPrice, depositPrice) != 0) return false;
        if (roomTypeStatus != roomType.roomTypeStatus) return false;
        if (description != null ? !description.equals(roomType.description) : roomType.description != null)
            return false;
        if (!Arrays.equals(roomTImg, roomType.roomTImg)) return false;
        if (roomName != null ? !roomName.equals(roomType.roomName) : roomType.roomName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = typeId;
        result = 31 * result + hostelId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(depositPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(roomTImg);
        result = 31 * result + (roomName != null ? roomName.hashCode() : 0);
        result = 31 * result + (roomTypeStatus ? 1 : 0);
        return result;
    }

    public RoomType() {
    }

    public RoomType(int hostelId, String description, double price, double depositPrice, byte[] roomTImg, String roomName, boolean roomTypeStatus) {
        this.hostelId = hostelId;
        this.description = description;
        this.price = price;
        this.depositPrice = depositPrice;
        this.roomTImg = roomTImg;
        this.roomName = roomName;
        this.roomTypeStatus = roomTypeStatus;
    }

    public RoomType(int typeId, int hostelId, String description, double price, double depositPrice, byte[] roomTImg, String roomName, boolean roomTypeStatus) {
        this.typeId = typeId;
        this.hostelId = hostelId;
        this.description = description;
        this.price = price;
        this.depositPrice = depositPrice;
        this.roomTImg = roomTImg;
        this.roomName = roomName;
        this.roomTypeStatus = roomTypeStatus;
    }
}
