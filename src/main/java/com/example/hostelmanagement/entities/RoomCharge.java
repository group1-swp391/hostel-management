package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_roomCharge", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class RoomCharge {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roomChargeID")
    private int roomChargeId;
    @Basic
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "startDate")
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    private Date endDate;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;

    @ManyToOne
    @JoinColumn(name = "roomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room roomByRoomId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "invoiceID", referencedColumnName = "invoiceID", insertable = false, updatable = false, nullable = false)
    private Invoice invoiceByInvoiceId;

}
