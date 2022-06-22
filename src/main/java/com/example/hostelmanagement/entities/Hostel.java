package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tbl_Hostel", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Hostel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "hostelID")
    private int hostelId;
    @Basic
    @Column(name = "ownerHostelID")
    private int ownerHostelId;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "hostelName")
    private String hostelName;
    @Basic
    @Column(name = "hostelStatus")
    private boolean hostelStatus;
    @ManyToOne
    @JoinColumn(name = "ownerHostelID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User usersByOwnerHostelId;
    @OneToMany(mappedBy = "hostelByHostelId")
    private Collection<RoomType> roomTypesByHostelId;
    @OneToMany(mappedBy = "hostelByHostelId")
    private Collection<ServiceType> serviceTypesByHostelId;
    @OneToMany(mappedBy = "hostelByHostelId")
    private Collection<UtilityType> utilityTypesByHostelId;

}
