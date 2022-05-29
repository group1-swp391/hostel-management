package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
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
    @Column(name = "address")
    private String address;
    @Column(name = "userName")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "roleID")
    private String roleID;
    @Column(name = "userStatus")
    private byte userStatus;

    public User() {
    }

    public boolean getGender() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(byte userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public byte getStatus() {
        return userStatus;
    }

    public void setStatus(byte userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", REGTIME=" + REGTIME +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", userName='" + userName + '\'' +
//                ", password='" + password + '\'' +
                ", roleID='" + roleID + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
