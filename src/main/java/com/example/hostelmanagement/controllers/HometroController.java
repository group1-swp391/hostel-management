package com.example.hostelmanagement.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "api/v1/hometro")
public class HometroController {

    @RequestMapping(value = "user")
    public String renterSite() {
        return "user";
    }

    @RequestMapping(value = "to-room")
    public String rentRoomSite() {
        return "redirect:/api/v1/rentroom/list-room";
    }

    @RequestMapping(value = "money")
    public String moneySite() {
        return "redirect:/api/v1/invoice/money";
    }

    @RequestMapping(value = "info")
    public String roomInfo() {
        return "inforoom";
    }

    @RequestMapping(value = "invoice")
    public String historySite() {
        return "redirect:/api/v1/invoice/";
    }

    @RequestMapping(value = "roomcharge")
    public String getRoomChargeSite() {
        return "redirect:/api/v1/roomcharge/";
    }

    @RequestMapping(value = "history-booking")
    public String historyBookingSite(){ return "redirect:/api/v1/booking/"; }

    @RequestMapping(value = "view-contract")
    public String getContractSite(){ return "redirect:/api/v1/contract/viewcontract"; }

    @GetMapping(value = "view-contract-detail/{id}")
    public String getDetailContractSite(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("contractID", id);
        return "redirect:/api/v1/contract/viewcontractdetail";
    }
    @RequestMapping(value = "history-invoice")
    public String historyInvoiceSite(){ return "redirect:/api/v1/invoice/historyinvoice"; }
    @GetMapping("view-invoice-detail/{id}")
    public String getDetailInvoiceSite(@PathVariable("id") int id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("invoiceID",id);
        return "redirect:/api/v1/invoice/viewinvoicedetail"; }


}

