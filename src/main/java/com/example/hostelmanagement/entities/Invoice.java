package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "tbl_Invoice", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Invoice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "invoiceID")
    private int invoiceId;
    @Basic
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "userID")
    private Integer userId;
    @Basic
    @Column(name = "invoiceName")
    private String invoiceName;
    @Basic
    @Column(name = "totalAmount")
    private double totalAmount;
    @Basic
    @Column(name = "invoiceStatus")
    private boolean invoiceStatus;
    @Basic
    @Column(name = "note")
    private String note;
    @Basic
    @Column(name = "invoiceCreateDate")
    private Timestamp invoiceCreateDate;
    @Basic
    @Column(name = "paymentStatus")
    private boolean paymentStatus;
    @Basic
    @Column(name = "paymentDate")
    private Timestamp paymentDate;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User usersByUserId;
    @ManyToOne
    @JoinColumn(name = "roomID", referencedColumnName = "roomID", insertable = false, updatable = false)
    private Room roomByRoomId;
    @OneToMany(mappedBy = "invoiceByInvoiceId")
    private Collection<UsedService> usedServicesByInvoiceId;
    @OneToMany(mappedBy = "invoiceByInvoiceId")
    private Collection<UsedUtility> tblUsedUtilitiesByInvoiceId;
    @OneToMany(mappedBy = "invoiceByInvoiceId")
    private Collection<RoomCharge> roomChargesByInvoiceId;


}
