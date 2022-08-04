package com.example.hostelmanagement.utils;

import com.example.hostelmanagement.entities.Hostel;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.repositories.HostelRepository;

import java.util.*;

public class WriteChartUtils {
    private static List<Hostel> hostel = null;
    public static void getHostelList(HostelRepository hostelRepository, User loginUser) {
        hostel = hostelRepository.findAllByOwnerHostelIdAndHostelStatusIsTrue(loginUser.getUserId());
    }
    public static HashMap<Integer, Double> getPricePerMonth(Set<Integer> listYears, int year, Calendar cal){
        HashMap<Integer, Double> invoicePricePerMonth = new HashMap<>();
        hostel.forEach(h -> h.getRoomTypesByHostelId().
                forEach(rt -> rt.getRoomsByTypeId().
                        forEach(r -> r.getInvoicesByRoomId().
                                forEach(i -> {
                                    if (i.getPaymentDate()!=null) {
                                        long timestamp = i.getPaymentDate().getTime();
                                        cal.setTimeInMillis(timestamp);
                                        listYears.add(cal.get(Calendar.YEAR));
                                        if (invoicePricePerMonth.containsKey(cal.get(Calendar.MONTH)+1) && cal.get(Calendar.YEAR) == year){
                                            invoicePricePerMonth.put(cal.get(Calendar.MONTH)+1, invoicePricePerMonth.get(cal.get(Calendar.MONTH)+1)+i.getTotalAmount());
                                        }else if (cal.get(Calendar.YEAR) == year){
                                            invoicePricePerMonth.put(cal.get(Calendar.MONTH)+1, i.getTotalAmount());
                                        }
                                    }
                                }))));
        return invoicePricePerMonth;
    }
    public static HashMap<String, Double> getPriceOfEachHostel() {
        HashMap<String, Double> amountOfEachHostel = new HashMap<>();
        hostel.forEach(h -> h.getRoomTypesByHostelId().
        forEach(rt -> rt.getRoomsByTypeId().
                forEach(r -> r.getInvoicesByRoomId().
                        forEach(i -> {
                            if (i.getPaymentDate()!=null) {
                                if (amountOfEachHostel.containsKey(h.getHostelName())) {
                                    amountOfEachHostel.put(h.getHostelName(), amountOfEachHostel.get(h.getHostelName())+i.getTotalAmount());
                                }else {
                                    amountOfEachHostel.put(h.getHostelName(), i.getTotalAmount());
                                }
                            }
                        }))));
    return amountOfEachHostel;
    }
    public static HashMap<String, Integer> getTotalUserInHostel() {
        HashMap<String, Integer> usersInHostel = new HashMap<>();
        hostel.forEach(h -> h.getRoomTypesByHostelId().
                forEach(rt -> rt.getRoomsByTypeId().
                        forEach(r -> {
                            if (r.getUserId()!=null) {
                                if (usersInHostel.containsKey(h.getHostelName())){
                                    usersInHostel.put(h.getHostelName(), usersInHostel.get(h.getHostelName())+1);
                                }
                                else{
                                    usersInHostel.put(h.getHostelName(), 1);
                                }
                            }
                        })));
        return usersInHostel;
    }
    public static HashMap<String, int[]> getTotalRoomStatus() {
        HashMap<String, int[]> totalRoomStatus = new HashMap<>();
        hostel.forEach(h -> h.getRoomTypesByHostelId().
                forEach(rt -> rt.getRoomsByTypeId().
                        forEach(r -> {
                            if (r.getUserId()==null) {
                                if (totalRoomStatus.containsKey(h.getHostelName())){
                                    int[] temp_arr = totalRoomStatus.get(h.getHostelName());
                                    temp_arr[0]++;
                                    totalRoomStatus.put(h.getHostelName(), temp_arr);
                                }
                                else{
                                    totalRoomStatus.put(h.getHostelName(), new int[]{0,0});
                                }
                            }else{
                                if (totalRoomStatus.containsKey(h.getHostelName())){
                                    int[] temp_arr = totalRoomStatus.get(h.getHostelName());
                                    temp_arr[1]++;
                                    totalRoomStatus.put(h.getHostelName(), temp_arr);
                                }
                                else{
                                    totalRoomStatus.put(h.getHostelName(), new int[]{0,0});
                                }
                            }
                        })));
        return totalRoomStatus;
    }
}
