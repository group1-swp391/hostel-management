package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tbl_UtilityType", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class UtilityType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "utilityTypeID")
    private int utilityTypeId;
    @Basic
    @Column(name = "utilityName")
    private String utilityName;
    @Basic
    @Column(name = "pricePerIndex")
    private Double pricePerIndex;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;
    @OneToMany(mappedBy = "utilityTypeByUtilityTypeId")
    private Collection<UsedUtility> usedUtilitiesByUtilityTypeId;
    @ManyToOne
    @JoinColumn(name = "hostelID", referencedColumnName = "hostelID", insertable = false, updatable = false)
    private Hostel hostelByHostelId;


}
