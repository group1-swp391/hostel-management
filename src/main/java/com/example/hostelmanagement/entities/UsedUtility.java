package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_UsedUtility", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UsedUtility {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usedUtilityID")
    private int usedUtilityId;
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
    @Column(name = "utilityTypeID")
    private int utilityTypeId;
    @Basic
    @Column(name = "oldIndex")
    private int oldIndex;
    @Basic
    @Column(name = "newIndex")
    private int newIndex;
    @Basic
    @Column(name = "pricePerIndex")
    private double pricePerIndex;
    @Basic
    @Column(name = "invoiceID")
    private Integer invoiceId;
    @ManyToOne
    @JoinColumn(name = "roomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room roomByRoomId;
    @ManyToOne
    @JoinColumn(name = "utilityTypeID", referencedColumnName = "utilityTypeID", insertable = false, updatable = false)
    private UtilityType utilityTypeByUtilityTypeId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "invoiceID", referencedColumnName = "invoiceID", insertable = false, updatable = false)
    @ToString.Exclude
    private Invoice invoiceByInvoiceId;


}
