package com.example.hostelmanagement.utils.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    public String getFormMail(String hostelName, String address, String roomTypeName, int roomNumber, String fullName, String phone, Date appointmentDate, String message){
        return "EMAIL CONFIRMATION\n-----------------------------------------------------------------\nRenter name: "+fullName+
                "\nPhone: "+phone +
                "\nAppointment Date: "+ appointmentDate +
                "\nHostel: " + hostelName +
                "\nAddress: "+ address +
                "\nType of room: " + roomTypeName +
                "\nRoom Number: " + roomNumber +
                "\nAdditional Request: "+message+
                "\n-----------------------------------------------------------------"+
                "\nThanks for trust our services!";
    }
}
