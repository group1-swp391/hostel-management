package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.models.Contract;
import com.example.hostelmanagement.models.TblInvoiceEntity;
import com.example.hostelmanagement.models.User;
import com.example.hostelmanagement.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "api/v1/User/")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping(value = "showInfo")
    public String showInfo(HttpSession session) {
        if (session.getAttribute("LOGIN_USER")==null) {
            return "login";
        }
        return "userinfo";
    }

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    public String login(@RequestParam(name = "userName",required = false) String userName, @RequestParam(name = "password",required = false) String password, HttpSession session, ModelMap mm) {
        User user = userRepository.getUserByUserNameAndPassword(userName, password);
        if(user!=null) {
            session.setAttribute("LOGIN_USER", user);
            if (user.getRoleID()==2) {
                return "admin_userMngt";
            }
            return "index";
        } else {
            mm.put("message", "Invalid account");
            return "login";
        }
    }
    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }
    @RequestMapping(value = "register")
    public String register(@RequestParam("hostelId") String hostelId, ModelMap mm) {
        mm.put("hostelId", hostelId);
        return "register";
    }
    @PostMapping(value = "register")
    public String register(@ModelAttribute(value = "user") User user, ModelMap mm) {
        if (!user.getUserName().equals("long")) {
            mm.put("error", "Register failed");
            return "register";
        }
        mm.put("message", user.getUserName());
        return "welcome";
    }
    @GetMapping(value = "searchName")
    public String searchName(@RequestParam(name = "userName",required = false) String userName, ModelMap mm) {
        try {
            mm.put("userName", userName);
            List<User> users = userRepository.getAllByAName(userName);
            if (users.isEmpty()) {
                mm.put("message", "No result");
            }else {
                mm.put("message","Total result: "+users.size());
                mm.put("users", users);
            }
        } catch (Exception e) {
            System.out.println("error");
        } finally {
            return "admin_userMngt";
        }
    }
    @GetMapping(value = "history/{id}")
    public String history(@PathVariable(name="id",required = false) int id, HttpSession session, ModelMap mm){
        try {

            List<Contract> history = userRepository.getHistoryOfUserByUserID(id);
            if(!history.isEmpty())
                mm.put("listHistory", history);
            else
                mm.put("message","history empty");
        }
        catch (Exception e){


 }
        finally {
            return "history";
        }

    }
    @GetMapping(value = "historyContract/{iduser}/{idcontract}")
    public String viewContract(@PathVariable(name = "iduser",required = false) int iduser,@PathVariable(name = "idcontract",required = false) int idcontract,HttpSession session,ModelMap mm){
        try {
            Contract c = userRepository.getContract(iduser,idcontract);
            mm.put("contract",c);
        }catch (Exception e){

        }
        return "historyContract";
    }
    @GetMapping(value = "historybill/{iduser}/{idcontract}")
    public String viewAllHistoryBill(@PathVariable(name = "iduser",required = false) int iduser,@PathVariable(name = "idcontract",required = false) int idcontract,HttpSession session,ModelMap mm){
        try {
            List<TblInvoiceEntity> bill = userRepository.getHistoryBillByUserID(iduser,idcontract);
            mm.put("historybill",bill);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "historybill";
    }
}
