package com.example.hostelmanagement.entities;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_Contracts", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder

public class Contracts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "contractID")
    private int contractId;
    @Basic
    @Column(name = "startDate")
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    private Date endDate;
    @Basic
    @Column(name = "deposit")
    private double deposit;
    @Basic
    @Column(name = "userID")
    private int userId;
    @Basic
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "contractStatus")
    private Boolean contractStatus;
    @Basic
    @Column(name = "depositPaymentStatus")
    private Boolean depositPaymentStatus;
    @Basic
    @Column(name = "createContractTime")
    private Timestamp createContractTime;
    @Basic
    @Column(name = "contractLiquidationTime")
    private Timestamp contractLiquidationTime;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User usersByUserId;
    @ManyToOne
    @JoinColumn(name = "roomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room roomByRoomId;
}
