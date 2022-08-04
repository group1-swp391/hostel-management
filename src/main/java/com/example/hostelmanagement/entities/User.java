package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name = "tbl_Users", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID")
    private int userId;
    @Basic
    @Column(name = "userName")
    private String userName;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "fullName")
    private String fullName;
    @Basic
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Basic
    @Column(name = "gender")
    private Boolean gender;
    @Basic
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "documentID")
    private String documentId;
    @Basic
    @Column(name = "documentFrontSide")
    private byte[] documentFrontSide;
    @Basic
    @Column(name = "documentBackSide")
    private byte[] documentBackSide;
    @Basic
    @Column(name = "roleID")
    private int roleId;
    @Basic
    @Column(name = "userStatus")
    private boolean userStatus;
    @Basic
    @Column(name = "REGTIME")
    private Date regtime;

    @OneToMany(mappedBy = "usersByUserId")
    private Collection<Contracts> contractsByUserId;
    @OneToMany(mappedBy = "usersByOwnerHostelId")
    private Collection<Hostel> hostelsByUserId;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<Room> roomsByUserId;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<Invoice> invoicesByUserId;
    @ManyToOne
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", insertable = false, updatable = false)
    private Role roleByRoleId;

}
