package com.example.hostelmanagement.entities;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tbl_RoomType", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
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
    @OneToMany(mappedBy = "roomTypeByTypeId")
    @Where(clause = "roomStatus = 1")
    private Collection<Room> roomsByTypeId;

    @OneToMany(mappedBy = "roomTypeByTypeId")
    @Where(clause = "roomStatus = 1 AND UserID IS NULL")
    private Collection<Room> roomsEmpty;
    @ManyToOne
    @JoinColumn(name = "hostelID", referencedColumnName = "hostelID", insertable = false, updatable = false)
    private Hostel hostelByHostelId;

}
