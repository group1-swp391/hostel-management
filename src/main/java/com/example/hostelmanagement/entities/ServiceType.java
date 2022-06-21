package com.example.hostelmanagement.entities;

import javax.persistence.*;

@Entity
@Table(name = "tbl_ServiceType", schema = "dbo", catalog = "Hostel_Management")
public class ServiceType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "serviceTypeID")
    private int serviceTypeId;
    @Basic
    @Column(name = "serviceName")
    private String serviceName;
    @Basic
    @Column(name = "price")
    private Double price;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getHostelId() {
        return hostelId;
    }

    public void setHostelId(Integer hostelId) {
        this.hostelId = hostelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceType that = (ServiceType) o;

        if (serviceTypeId != that.serviceTypeId) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (hostelId != null ? !hostelId.equals(that.hostelId) : that.hostelId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceTypeId;
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (hostelId != null ? hostelId.hashCode() : 0);
        return result;
    }

    public ServiceType() {
    }

    public ServiceType(String serviceName, Double price, Integer hostelId) {
        this.serviceName = serviceName;
        this.price = price;
        this.hostelId = hostelId;
    }

    public ServiceType(int serviceTypeId, String serviceName, Double price, Integer hostelId) {
        this.serviceTypeId = serviceTypeId;
        this.serviceName = serviceName;
        this.price = price;
        this.hostelId = hostelId;
    }
}
