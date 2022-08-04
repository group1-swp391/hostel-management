package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tbl_Room", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
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
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "image")
    private byte[] image;
    @Basic
    @Column(name = "roomStatus")
    private boolean roomStatus;
    @OneToMany(mappedBy = "roomByRoomId")
    private Collection<Contracts> contractsByRoomId;
    @OneToMany(mappedBy = "roomByRoomId")
    private Collection<Invoice> invoicesByRoomId;
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User usersByUserId;
    @ManyToOne
    @JoinColumn(name = "typeID", referencedColumnName = "typeID", insertable = false, updatable = false)
    private RoomType roomTypeByTypeId;
    @OneToMany(mappedBy = "roomByRoomId")
    private Collection<UsedService> usedServicesByRoomId;
    @OneToMany(mappedBy = "roomByRoomId")
    private Collection<UsedUtility> tblUsedUtilitiesByRoomId;
    @OneToMany(mappedBy = "roomByRoomId")
    private Collection<RoomCharge> roomChargesByRoomId;


}
