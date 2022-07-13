package com.example.hostelmanagement.utils.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequestMapping(value = "api/v1/accept-booking/")
public class EmailController {
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping(value = "accept")
    public String
    sendMail(EmailDetails details, @RequestParam String hostelName,@RequestParam String address, @RequestParam String roomTypeName,
            @RequestParam int roomNumber, @RequestParam String fullName,
             @RequestParam String email, @RequestParam String phone,
             @RequestParam Date appointmentDate, @RequestParam String message)
    {
        String status
                = emailService.sendSimpleMail(details,hostelName,address, roomTypeName, roomNumber,fullName,email,phone,appointmentDate,message);
        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details)
    {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }
}
