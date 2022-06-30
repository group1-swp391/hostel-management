package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Invoice;
import com.example.hostelmanagement.repositories.*;
import com.example.hostelmanagement.utils.Momo.config.Environment;
import com.example.hostelmanagement.utils.Momo.config.PartnerInfo;
import com.example.hostelmanagement.utils.Momo.enums.RequestType;
import com.example.hostelmanagement.utils.Momo.models.PaymentResponse;
import com.example.hostelmanagement.utils.Momo.processor.CreateOrderMoMo;
import com.example.hostelmanagement.utils.Momo.shared.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Target;


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
    @Autowired
    private InvoiceRepository invoiceRepository;

    @RequestMapping(value = "")
    public String getAdminHome(ModelMap mm,
                               HttpSession session,
                               @RequestParam int invoiceId,
                               @RequestParam String orderInfo
                               ) throws Exception {
        try {
            LogUtils.init();
            String requestId = String.valueOf(System.currentTimeMillis());
           // String invoiceId = "1";
            String orderId = String.valueOf(System.currentTimeMillis()) + "_InvoiceID" + "2";
            Invoice invoice = invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id:" + invoiceId));

            long amount = (long) invoice.getTotalAmount();

            //String orderInfo = "Thanh toan tien thang 6";
            String returnURL = "localhost/api/v1/payment&requestId=" + requestId + "&orderId=" + orderId;
            String notifyURL = "localhost/api/v1/";

            String partnerCode = "MOMOLRJZ20181206";
            String accessKey = "mTCKt9W3eU1m39TW";
            String secretKey = "SetA5RDnLHvt51AULf51DyauxUo3kDU6";
            String endPoint = "https://test-payment.momo.vn/v2/gateway/api";

            PartnerInfo partnerInfo = new PartnerInfo(partnerCode, accessKey, secretKey);
//            Environment.EnvTarget envTarget = new Environment.EnvTarget();

//            Environment environment1 = Environment.
          //  Environment environment1 = new Environment(endPoint, partnerInfo, "env");

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


