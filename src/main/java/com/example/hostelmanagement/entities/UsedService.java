package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_UsedService", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UsedService {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usedServiceID")
    private int usedServiceId;
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
    @Column(name = "servicetypeID")
    private int servicetypeId;
    @Basic
    @Column(name = "usedQuantity")
    private int usedQuantity;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;
    @ManyToOne
    @JoinColumn(name = "roomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room roomByRoomId;
    @ManyToOne
    @JoinColumn(name = "servicetypeID", referencedColumnName = "serviceTypeID", insertable = false, updatable = false)
    private ServiceType serviceTypeByServicetypeId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "invoiceID", referencedColumnName = "invoiceID", insertable = false, updatable = false)
    private Invoice invoiceByInvoiceId;



}
