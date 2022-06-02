package com.example.hostelmanagement.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "tbl_Contracts", schema = "dbo", catalog = "Hostel_Management7")
public class Contracts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "contractID")
    private Integer contractId;
    @Basic
    @Column(name = "startDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.sql.Date startDate;
    @Basic
    @Column(name = "endDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.sql.Date  endDate;
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
    private Date createContractTime;
    @Basic
    @Column(name = "contractLiquidationTime")
    private Date contractLiquidationTime;

    public Contracts() {
    }

    public Contracts(Date startDate, Date endDate, Double deposit, Integer userId, Integer roomId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.userId = userId;
        this.roomId = roomId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contracts that = (Contracts) o;
        return Objects.equals(contractId, that.contractId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(deposit, that.deposit) && Objects.equals(userId, that.userId) && Objects.equals(roomId, that.roomId) && Objects.equals(contractStatus, that.contractStatus) && Objects.equals(createContractTime, that.createContractTime) && Objects.equals(contractLiquidationTime, that.contractLiquidationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractId, startDate, endDate, deposit, userId, roomId, contractStatus, createContractTime, contractLiquidationTime);
    }
}
