package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Room;
import com.example.hostelmanagement.entities.UsedUtility;
import com.example.hostelmanagement.entities.User;
import com.example.hostelmanagement.entities.UtilityType;
import com.example.hostelmanagement.repositories.HostelRepository;
import com.example.hostelmanagement.repositories.RoomRepository;
import com.example.hostelmanagement.repositories.UsedUtilityRepository;
import com.example.hostelmanagement.repositories.UtilityTypeRepository;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "api/v1/used-utility")
public class UsedUtilityController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UsedUtilityRepository usedUtilityRepository;
    @Autowired
    private UtilityTypeRepository utilityTypeRepository;
    @Autowired
    private HostelRepository hostelRepository;

    @PostMapping(value ="/insert")
    public String insertUltility(@RequestParam int utilityTypeId,
                                 @RequestParam int roomId,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam int oldIndex,
                                 @RequestParam int newIndex,
                                 ModelMap mm,
                                 RedirectAttributes redirectAttributes
                                 )
    {
        try {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phòng có id: " + roomId));
            Collection<UtilityType> utilityTypes = room.getRoomTypeByTypeId().getHostelByHostelId().getUtilityTypesByHostelId();

            UtilityType utilityType = utilityTypeRepository.findById(utilityTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tiện ích có id: " + utilityTypeId));

            double pricePerIndex = utilityType.getPricePerIndex();

            UsedUtility usedUtility = UsedUtility.builder()
                                                .roomId(roomId)
                                                .startDate(startDate)
                                                .endDate(endDate)
                                                .oldIndex(oldIndex)
                                                .utilityTypeId(utilityTypeId)
                                                .newIndex(newIndex)
                                                .pricePerIndex(pricePerIndex)
                                                .build();
            usedUtilityRepository.save(usedUtility);

            //mm.put("message", "Thêm phòng sử dụng tiện ích " + utilityType.getUtilityName() + " thành công");

            redirectAttributes.addFlashAttribute("flashAttr","Thêm phòng sử dụng " +  utilityType.getUtilityName() + " thành công");
        }
        catch (Exception ex) {
            mm.put("message", "Lỗi thêm dịch vụ sử dụng" + ex.getMessage());
            return "error";
        }
        return "redirect:/api/v1/room/"+roomId;
    }

    @PostMapping(value ="/update")
    public String updateUltility(@RequestParam int usedUtilityId,
                                 @RequestParam int utilityTypeId,
                                 @RequestParam java.sql.Date startDate,
                                 @RequestParam java.sql.Date endDate,
                                 @RequestParam int oldIndex,
                                 @RequestParam int newIndex,
                                 @RequestParam double pricePerIndex,
                                 ModelMap mm,  RedirectAttributes redirectAttributes) {
        Optional<UsedUtility> usedUtilityOptional = usedUtilityRepository.findById(usedUtilityId);

        if (usedUtilityOptional.isPresent()) {
            //Validate and authorzation...
            UsedUtility usedUtility = usedUtilityOptional.get();
            usedUtility.setStartDate(startDate);
            usedUtility.setOldIndex(oldIndex);
            usedUtility.setEndDate(endDate);
            usedUtility.setNewIndex(newIndex);
            usedUtility.setUtilityTypeId(utilityTypeId);
            usedUtility.setPricePerIndex(pricePerIndex);
            usedUtilityRepository.save(usedUtility);

            redirectAttributes.addFlashAttribute("flashAttr","Cập nhật tiện ích sử dụng " + usedUtilityOptional.get().getUtilityTypeByUtilityTypeId().getUtilityName() + " thành công!");
            int roomId = usedUtilityOptional.get().getRoomId();
            return "redirect:/api/v1/room/"+roomId;
        } else {
            mm.put("message","Không tìm thấy tiện ích sử dụng");
            return "error";
        }

    }

    @PostMapping(value ="/delete")
    public String deleteUltility(@RequestParam int usedUtilityId,
                                 ModelMap mm, RedirectAttributes redirectAttributes) {
        Optional<UsedUtility> usedUtilityOptional = usedUtilityRepository.findById(usedUtilityId);
        if (usedUtilityOptional.isPresent()) {
            usedUtilityRepository.delete(usedUtilityOptional.get());

            redirectAttributes.addFlashAttribute("flashAttr","Xóa tiện ích sử dụng thành công");

            return "redirect:/api/v1/room/"+usedUtilityOptional.get().getRoomId();
        } else {
            mm.put("message","Không tìm thấy tiện ích sử dụng");
            return "error";
        }
    }
}
