package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "tbl_Room", schema = "dbo", catalog = "Hostel_Management7")
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roomID")
    private Integer roomId;
    @Basic
    @Column(name = "roomNumber")
    private Integer roomNumber;
    @Basic
    @Column(name = "UserID")
    private Integer userId;
    @Basic
    @Column(name = "typeID")
    private Integer typeId;
    @Basic
    @Column(name = "roomStatus")
    private Boolean roomStatus;
    @Basic
    @Column(name = "image")
    private byte[] image;

    public Room(Integer roomNumber, Integer userId, Integer typeId, Boolean roomStatus, byte[] image) {
        this.roomNumber = roomNumber;
        this.userId = userId;
        this.typeId = typeId;
        this.roomStatus = roomStatus;
        this.image = image;
    }

    public Room() {
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Boolean getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Boolean roomStatus) {
        this.roomStatus = roomStatus;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId) && Objects.equals(roomNumber, room.roomNumber) && Objects.equals(userId, room.userId) && Objects.equals(typeId, room.typeId) && Objects.equals(roomStatus, room.roomStatus) && Arrays.equals(image, room.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(roomId, roomNumber, userId, typeId, roomStatus);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
