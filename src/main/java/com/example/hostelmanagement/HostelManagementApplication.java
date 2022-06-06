package com.example.hostelmanagement;

import com.example.hostelmanagement.models.*;
import com.example.hostelmanagement.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

@SpringBootApplication
public class HostelManagementApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HostelRepository hostelRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ContractRepository contractRepository;

    public static void main(String[] args) {
        SpringApplication.run(HostelManagementApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        User u1 = userRepository.findByUserNameAndAndPassword("hukhho", "hunghung");
        //System.out.println(u1.getFullName());

        List<Hostel> hostels = hostelRepository.getAllByHostelStatusTrue();
        for (Hostel x : hostels) {
            //System.out.println(x.getHostelId());
        };

        List<RoomType> roomtypes = roomTypeRepository.getTblRoomTypeByRoomTypeStatusTrue();
        for (RoomType x : roomtypes) {
            //System.out.println(x.getRoomName() + " - " + x.getDescription());
        };
        List<RoomType> roomtypes1 = roomTypeRepository.getTblRoomTypeByRoomTypeStatusTrueAndHostelId(1);
        for (RoomType x : roomtypes1) {
            //System.out.println(x.getRoomName() + " - " + x.getDescription());
        };
        List<Room> rooms = roomRepository.getTblRoomByUserIdNullAndRoomStatusTrueAndTypeId(1);
        for (Room x : rooms) {
            System.out.println(x.getRoomId() + " - " + x.getRoomNumber());
        };
        String strDate1 = "2022-06-01";
        String strDate2 = "2023-06-01";

        //Converting String to Date

        Date date3 = Date.valueOf(LocalDate.of(2019, 01, 10));
        java.util.Date date4 = new java.util.Date();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//      Contracts x = new Contracts((java.sql.Date) date1, (java.sql.Date) date2, 5000000.0, 3, 1);

        //Contracts x = new Contracts(date3, date3, 500.00, 3, 1, false, timestamp, null);
        //Contracts x = new Contracts(date3, date3, 500.00, 3, 1, false, date4, null);
       // Contracts x =  new Contracts(date3, date3, 500.00, 3, 1, false, date4, null);
        //System.out.println(contractRepository.save(x));
        //System.out.println(x.toString());
        //contractRepository.save(x);
    }
}
