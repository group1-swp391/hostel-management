package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.repositories.*;
import com.example.hostelmanagement.utils.Momo.config.Environment;
import com.example.hostelmanagement.utils.Momo.enums.RequestType;
import com.example.hostelmanagement.utils.Momo.models.PaymentResponse;
import com.example.hostelmanagement.utils.Momo.processor.CreateOrderMoMo;
import com.example.hostelmanagement.utils.Momo.shared.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "api/v1/Payment")
public class PaymentController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private HostelRepository hostelRepository;

    @RequestMapping(value = "")
    public String getAdminHome(ModelMap mm) throws Exception {
        mm.put("mess","test");
        try {
            LogUtils.init();
            String requestId = String.valueOf(System.currentTimeMillis());
            String invoiceId = "1";
            String orderId = String.valueOf(System.currentTimeMillis()) + "_InvoiceID" + "2";
            long amount = 5000000;

            String orderInfo = "Thanh toan tien thang 6";
            String returnURL = "localhost/api/v1/payment&requestId=" + requestId + "&orderId=" + orderId;
            String notifyURL = "https://google.com.vn";

            Environment environment = Environment.selectEnv("dev");

//      Remember to change the IDs at enviroment.properties file

            //        Payment Method- Phương thức thanh toán
            PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET);

          //  orderId = String.valueOf(System.currentTimeMillis());
          //  requestId = String.valueOf(System.currentTimeMillis());
            //        Payment Method- Phương thức thanh toán
           //PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM);

          //  orderId = String.valueOf(System.currentTimeMillis());
         //   requestId = String.valueOf(System.currentTimeMillis());
            //        Payment Method- Phương thức thanh toán
           // PaymentResponse captureCreditMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_CREDIT);
            //mm.put("messages", captureWalletMoMoResponse.getPayUrl());
            return "redirect:" + captureWalletMoMoResponse.getPayUrl();

        } catch (Exception ex) {
            mm.put("messages", "Error");
        }

        return "test2";
    }





}


