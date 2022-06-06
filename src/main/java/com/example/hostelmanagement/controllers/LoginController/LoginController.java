package com.example.hostelmanagement.controllers.LoginController;

import com.example.hostelmanagement.models.AccountSession;
import com.example.hostelmanagement.models.User;
import com.example.hostelmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
@RequestMapping(value = "api/v1/Auth")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login")
    public String login(HttpSession session) {
        AccountSession accSession = (AccountSession) session.getAttribute("accSession");

        if (accSession != null) {
            return "index";
        } else {
            return "test/login";
        }
    }

    @RequestMapping(value = "/logout")
    public RedirectView logout(HttpSession session, ModelMap mm) {
        AccountSession accSession = (AccountSession) session.getAttribute("accSession");

        if (accSession != null) {
            session.removeAttribute("accSession");
        }
        return new RedirectView("/api/v1/Auth/login");
    }


    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName, @RequestParam("password") String password, @ModelAttribute(value = "user") User user, HttpSession session, ModelMap mm) {

        user = userRepository.findByUserNameAndAndPassword(userName, password);

        if (user != null) {
            AccountSession accSession = new AccountSession(user.getUserId(), user.getRoleId(), new Date());
            session.setAttribute("accSession",accSession);

            mm.put("message", accSession.getUserid() + " " + "role: " + accSession.getRoleid() + " logtime: " + accSession.getLogtime());

            return "welcome";
        }else {
            mm.put("message", "Invalid account");

            return "test/login";
        }
    }
}
