package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tbl_ServiceType", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ServiceType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "serviceTypeID")
    private int serviceTypeId;
    @Basic
    @Column(name = "serviceName")
    private String serviceName;
    @Basic
    @Column(name = "price")
    private Double price;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;
    @ManyToOne
    @JoinColumn(name = "hostelID", referencedColumnName = "hostelID", insertable = false, updatable = false)
    private Hostel hostelByHostelId;

    @OneToMany(mappedBy = "serviceTypeByServicetypeId")
    private Collection<UsedService> usedServicesByServiceTypeId;
}
