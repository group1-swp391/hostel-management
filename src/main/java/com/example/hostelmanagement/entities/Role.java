package com.example.hostelmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tbl_Role", schema = "dbo", catalog = "Hostel_Management")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roleID")
    private int roleId;
    @Basic
    @Column(name = "roleName")
    private String roleName;
    @OneToMany(mappedBy = "roleByRoleId")
    private Collection<User> usersByRoleId;


}
