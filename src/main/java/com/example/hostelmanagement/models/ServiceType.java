package com.example.hostelmanagement.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tbl_ServiceType", schema = "dbo", catalog = "Hostel_Management7")
public class ServiceType {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "serviceTypeID")
    private Integer serviceTypeId;
    @Basic
    @Column(name = "serviceName")
    private String serviceName;
    @Basic
    @Column(name = "price")
    private Double price;
    @Basic
    @Column(name = "hostelID")
    private Integer hostelId;

    public ServiceType() {
    }

    public ServiceType(String serviceName, Double price, Integer hostelId) {

        this.serviceName = serviceName;
        this.price = price;
        this.hostelId = hostelId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
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
        return Objects.equals(serviceTypeId, that.serviceTypeId) && Objects.equals(serviceName, that.serviceName) && Objects.equals(price, that.price) && Objects.equals(hostelId, that.hostelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceTypeId, serviceName, price, hostelId);
    }
}
