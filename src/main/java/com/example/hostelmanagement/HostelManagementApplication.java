package com.example.hostelmanagement;

import com.example.hostelmanagement.models.*;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.RoomTypeRepository;
import com.example.hostelmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        List<Room> rooms = roomRepository.getTblRoomByRoomStatusTrueAndTypeId(1);
        for (Room x : rooms) {
            System.out.println(x.getRoomId() + " - " + x.getRoomNumber());
        };
        String strDate1 = "2022-06-01";
        String strDate2 = "2023-06-01";

        //Converting String to Date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        Date date1 = formatter.parse(strDate1);
        Date date2 = formatter.parse(strDate2);

//        Contracts x = new Contracts((java.sql.Date) date1, (java.sql.Date) date2, 5000000.0, 3, 1);
        System.out.println(date1);
    }
}
