package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.models.Contract;
import com.example.hostelmanagement.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "api/v1/Contract/")
public class ContractController {
    @Autowired
    private ContractRepository contractRepository;
    @GetMapping(value = "history/{iduser}")
    public String viewListContract(@PathVariable(name = "iduser",required = false) int iduser, HttpSession session, ModelMap mm){
        try {
            if(contractRepository.findAllByContractByUserID(iduser).isEmpty()){
                mm.put("message","no history");
            }
            else
                mm.put("listContract",contractRepository.findAllByContractByUserID(iduser));
        }catch (Exception e){

        }
        return "history";
    }
    @GetMapping(value = "historycontract/{iduser}/{idcontract}")


}
