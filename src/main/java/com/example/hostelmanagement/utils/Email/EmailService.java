package com.example.hostelmanagement.utils.Email;


// Importing required classes

import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

// Interface
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details, String hostelName, String address, String roomTypeName, int roomNumber,String fullName,
                          String email, String phone,
                           Date appointmentDate, String message);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}