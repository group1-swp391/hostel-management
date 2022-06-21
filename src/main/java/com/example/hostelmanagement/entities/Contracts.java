package com.example.hostelmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_Contracts", schema = "dbo", catalog = "Hostel_Management")
public class Contracts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "contractID")
    private Integer contractId;
    @Basic
    @Column(name = "startDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date  endDate;
    @Basic
    @Column(name = "deposit")
    private Double deposit;
    @Basic
    @Column(name = "userID")
    private Integer userId;
    @Basic
    @Column(name = "roomID")
    private Integer roomId;
    @Basic
    @Column(name = "contractStatus")
    private Boolean contractStatus;
    @Basic
    @Column(name = "createContractTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp createContractTime;
    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp  contractLiquidationTime;

    public Contracts() {
    }

    public Contracts(Date startDate, Date endDate, Double deposit, Integer userId, Integer roomId, Boolean contractStatus, Timestamp createContractTime, Timestamp contractLiquidationTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.userId = userId;
        this.roomId = roomId;
        this.contractStatus = contractStatus;
        this.createContractTime = createContractTime;
        this.contractLiquidationTime = contractLiquidationTime;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
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

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Boolean getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(Boolean contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Timestamp getCreateContractTime() {
        return createContractTime;
    }

    public void setCreateContractTime(Timestamp createContractTime) {
        this.createContractTime = createContractTime;
    }

    public Timestamp getContractLiquidationTime() {
        return contractLiquidationTime;
    }

    public void setContractLiquidationTime(Timestamp contractLiquidationTime) {
        this.contractLiquidationTime = contractLiquidationTime;
    }

    @Override
    public String toString() {
        return "Contracts{" +
                "contractId=" + contractId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", deposit=" + deposit +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", contractStatus=" + contractStatus +
                ", createContractTime=" + createContractTime +
                ", contractLiquidationTime=" + contractLiquidationTime +
                '}';
    }
}
