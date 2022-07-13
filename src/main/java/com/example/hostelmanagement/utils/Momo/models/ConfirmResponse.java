package com.example.hostelmanagement.utils.Momo.models;


import com.example.hostelmanagement.utils.Momo.enums.ConfirmRequestType;

public class ConfirmResponse extends Response {
    private Long amount;
    private Long transId;
    private String requestId;
    private ConfirmRequestType requestType;
}
