package com.example.hostelmanagement.models;

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
    @Column(name = "roomStatus")
    private boolean roomStatus;
    @Basic
    @Column(name = "image")
    private byte[] image;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;

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

        Room room = (Room) o;

        if (roomId != room.roomId) return false;
        if (roomNumber != room.roomNumber) return false;
        if (typeId != room.typeId) return false;
        if (roomStatus != room.roomStatus) return false;
        if (userId != null ? !userId.equals(room.userId) : room.userId != null) return false;
        if (!Arrays.equals(image, room.image)) return false;
        if (hostelId != null ? !hostelId.equals(room.hostelId) : room.hostelId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomId;
        result = 31 * result + roomNumber;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + typeId;
        result = 31 * result + (roomStatus ? 1 : 0);
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + (hostelId != null ? hostelId.hashCode() : 0);
        return result;
    }
}
