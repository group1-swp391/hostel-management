package com.example.hostelmanagement.models;

import com.sun.javafx.geom.transform.Identity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_Contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contractID;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "deposit")
    private float deposit;

    @Column(name = "userID")
    private int userID;

    @Column(name = "roomID")
    private int roomID;

    @Column(name = "contractStatus")
    private String contractStatus;

    @Column(name = "createContractTime")
    private Date createContractTime;

    @Column(name = "contractLiquidationTime")
    private Date contractLiquidationTime;

    public Contract() {
    }

    public Contract(int contractID, Date startDate, Date endDate, float deposit, int userID, int roomID, String contractStatus, Date createContractTime, Date contractLiquidationTime) {
        this.contractID = contractID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.userID = userID;
        this.roomID = roomID;
        this.contractStatus = contractStatus;
        this.createContractTime = createContractTime;
        this.contractLiquidationTime = contractLiquidationTime;
    }

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Date getCreateContractTime() {
        return createContractTime;
    }

    public void setCreateContractTime(Date createContractTime) {
        this.createContractTime = createContractTime;
    }

    public Date getContractLiquidationTime() {
        return contractLiquidationTime;
    }

    public void setContractLiquidationTime(Date contractLiquidationTime) {
        this.contractLiquidationTime = contractLiquidationTime;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
