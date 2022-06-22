package com.example.hostelmanagement.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_Contracts", schema = "dbo", catalog = "Hostel_Management")
public class Contracts {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "contractID")
    private int contractId;
    @Basic
    @Column(name = "startDate")
    private Date startDate;
    @Basic
    @Column(name = "endDate")
    private Date endDate;
    @Basic
    @Column(name = "deposit")
    private double deposit;
    @Basic
    @Column(name = "userID")
    private int userId;
    @Basic
    @Column(name = "roomID")
    private int roomId;
    @Basic
    @Column(name = "contractStatus")
    private boolean contractStatus;
    @Basic
    @Column(name = "depositPaymentStatus")
    private Boolean depositPaymentStatus;
    @Basic
    @Column(name = "createContractTime")
    private Timestamp createContractTime;
    @Basic
    @Column(name = "contractLiquidationTime")
    private Timestamp contractLiquidationTime;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
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

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public boolean isContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(boolean contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Boolean getDepositPaymentStatus() {
        return depositPaymentStatus;
    }

    public void setDepositPaymentStatus(Boolean depositPaymentStatus) {
        this.depositPaymentStatus = depositPaymentStatus;
    }

    public Timestamp  getCreateContractTime() {
        return createContractTime;
    }

    public void setCreateContractTime(Timestamp createContractTime) {
        this.createContractTime = createContractTime;
    }

    public Timestamp  getContractLiquidationTime() {
        return contractLiquidationTime;
    }

    public void setContractLiquidationTime(Timestamp contractLiquidationTime) {
        this.contractLiquidationTime = contractLiquidationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contracts contracts = (Contracts) o;

        if (contractId != contracts.contractId) return false;
        if (Double.compare(contracts.deposit, deposit) != 0) return false;
        if (userId != contracts.userId) return false;
        if (roomId != contracts.roomId) return false;
        if (contractStatus != contracts.contractStatus) return false;
        if (startDate != null ? !startDate.equals(contracts.startDate) : contracts.startDate != null) return false;
        if (endDate != null ? !endDate.equals(contracts.endDate) : contracts.endDate != null) return false;
        if (depositPaymentStatus != null ? !depositPaymentStatus.equals(contracts.depositPaymentStatus) : contracts.depositPaymentStatus != null)
            return false;
        if (createContractTime != null ? !createContractTime.equals(contracts.createContractTime) : contracts.createContractTime != null)
            return false;
        if (contractLiquidationTime != null ? !contractLiquidationTime.equals(contracts.contractLiquidationTime) : contracts.contractLiquidationTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = contractId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        temp = Double.doubleToLongBits(deposit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + userId;
        result = 31 * result + roomId;
        result = 31 * result + (contractStatus ? 1 : 0);
        result = 31 * result + (depositPaymentStatus != null ? depositPaymentStatus.hashCode() : 0);
        result = 31 * result + (createContractTime != null ? createContractTime.hashCode() : 0);
        result = 31 * result + (contractLiquidationTime != null ? contractLiquidationTime.hashCode() : 0);
        return result;
    }

    public Contracts(Date startDate, Date endDate, double deposit, int userId, int roomId, boolean contractStatus, Boolean depositPaymentStatus, Timestamp createContractTime, Timestamp contractLiquidationTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.deposit = deposit;
        this.userId = userId;
        this.roomId = roomId;
        this.contractStatus = contractStatus;
        this.depositPaymentStatus = depositPaymentStatus;
        this.createContractTime = createContractTime;
        this.contractLiquidationTime = contractLiquidationTime;
    }
}
