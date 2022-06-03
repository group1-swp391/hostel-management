package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;

@Entity
@Table(name = "tbl_Users", schema = "dbo", catalog = "Hostel_Management")
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (roleId != user.roleId) return false;
        if (userStatus != user.userStatus) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(user.dateOfBirth) : user.dateOfBirth != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (documentId != null ? !documentId.equals(user.documentId) : user.documentId != null) return false;
        if (!Arrays.equals(documentFrontSide, user.documentFrontSide)) return false;
        if (!Arrays.equals(documentBackSide, user.documentBackSide)) return false;
        if (regtime != null ? !regtime.equals(user.regtime) : user.regtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (documentId != null ? documentId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(documentFrontSide);
        result = 31 * result + Arrays.hashCode(documentBackSide);
        result = 31 * result + roleId;
        result = 31 * result + (userStatus ? 1 : 0);
        result = 31 * result + (regtime != null ? regtime.hashCode() : 0);
        return result;
    }

    public User(int userId, String userName, String password, String fullName, Date dateOfBirth, Boolean gender, String phone, String email, String documentId, byte[] documentFrontSide, byte[] documentBackSide, int roleId, boolean userStatus, Date regtime) {
        this.userId = userId;
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

    public User(String userName, String password, String fullName, Date dateOfBirth, Boolean gender, String phone, String email, String documentId, byte[] documentFrontSide, byte[] documentBackSide, int roleId, boolean userStatus, Date regtime) {
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
}
