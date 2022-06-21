package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_RoomType", schema = "dbo", catalog = "Hostel_Management7")
public class RoomType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "typeID")
    private Integer typeId;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "price")
    private Double price;
    @Basic
    @Column(name = "depositPrice")
    private Double depositPrice;
    @Basic
    @Column(name = "roomName")
    private String roomName;
    @Basic
    @Column(name = "roomTypeStatus")
    private Boolean roomTypeStatus;
    @Basic
    @Column(name = "imageRoomType")
    private String imageRoomType;

    public RoomType() {
    }

    public RoomType(Integer hostelId, String description, Double price, Double depositPrice, String roomName, Boolean roomTypeStatus, String imageRoomType) {

        this.hostelId = hostelId;
        this.description = description;
        this.price = price;
        this.depositPrice = depositPrice;
        this.roomName = roomName;
        this.roomTypeStatus = roomTypeStatus;
        this.imageRoomType = imageRoomType;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getHostelId() {
        return hostelId;
    }

    public void setHostelId(Integer hostelId) {
        this.hostelId = hostelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(Double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Boolean getRoomTypeStatus() {
        return roomTypeStatus;
    }

    public void setRoomTypeStatus(Boolean roomTypeStatus) {
        this.roomTypeStatus = roomTypeStatus;
    }

    public String getImageRoomType() {
        return imageRoomType;
    }

    public void setImageRoomType(String imageRoomType) {
        this.imageRoomType = imageRoomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomType that = (RoomType) o;
        return Objects.equals(typeId, that.typeId) && Objects.equals(hostelId, that.hostelId) && Objects.equals(description, that.description) && Objects.equals(price, that.price) && Objects.equals(depositPrice, that.depositPrice) && Objects.equals(roomName, that.roomName) && Objects.equals(roomTypeStatus, that.roomTypeStatus) && Objects.equals(imageRoomType, that.imageRoomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, hostelId, description, price, depositPrice, roomName, roomTypeStatus, imageRoomType);
    }
}
