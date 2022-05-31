package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "tbl_Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    @Column(name = "REGTIME")
    private Date REGTIME;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "userName")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "roleID")
    private int roleID;
    @Column(name = "documentID")
    private String documentID;
    @Column(name = "documentFrontSide")
    private byte[] documentFrontSide;
    @Column(name = "documentBackSide")
    private byte[] documentBackSide;
    @Column(name = "userStatus")
    private boolean userStatus;

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getREGTIME() {
        return REGTIME;
    }

    public void setREGTIME(Date REGTIME) {
        this.REGTIME = REGTIME;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
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

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", REGTIME=" + REGTIME +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", roleID='" + roleID + '\'' +
                ", documentID='" + documentID + '\'' +
                ", documentFrontSide=" + Arrays.toString(documentFrontSide) +
                ", documentBackSide=" + Arrays.toString(documentBackSide) +
                ", userStatus=" + userStatus +
                '}';
    }
}
