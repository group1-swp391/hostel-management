package com.example.hostelmanagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "tbl_Users", schema = "dbo", catalog = "Hostel_Management7")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID")
    private Integer userId;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.sql.Date dateOfBirth;
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
    private Integer roleId;
    @Basic
    @Column(name = "userStatus")
    private Boolean userStatus;
    @Basic
    @Column(name = "REGTIME")
    private Timestamp regtime;

    public User(String userName, String password, String fullName, Date dateOfBirth, Boolean gender, String phone, String email, String documentId, byte[] documentFrontSide, byte[] documentBackSide, Integer roleId, Boolean userStatus, Timestamp regtime) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.documentId = documentId;
        this.documentFrontSide = documentFrontSide;
        this.documentBackSide = documentBackSide;
        this.roleId = roleId;
        this.userStatus = userStatus;
        this.regtime = regtime;
    }

    public User() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public byte[] getDocumentFrontSide() {
        return documentFrontSide;
    }

    public void setDocumentFrontSide(byte[] documentFrontSide) {
        this.documentFrontSide = documentFrontSide;
    }

    public byte[] getDocumentBackSide() {
        return documentBackSide;
    }

    public void setDocumentBackSide(byte[] documentBackSide) {
        this.documentBackSide = documentBackSide;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public Timestamp getRegtime() {
        return regtime;
    }

    public void setRegtime(Timestamp regtime) {
        this.regtime = regtime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", documentId='" + documentId + '\'' +
                ", documentFrontSide=" + Arrays.toString(documentFrontSide) +
                ", documentBackSide=" + Arrays.toString(documentBackSide) +
                ", roleId=" + roleId +
                ", userStatus=" + userStatus +
                ", regtime=" + regtime +
                '}';
    }
}