package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.Contracts;
import com.example.hostelmanagement.entities.Invoice;
import com.example.hostelmanagement.repositories.*;
import com.example.hostelmanagement.utils.Momo.config.Environment;
import com.example.hostelmanagement.utils.Momo.config.PartnerInfo;
import com.example.hostelmanagement.utils.Momo.enums.RequestType;
import com.example.hostelmanagement.utils.Momo.models.ConfirmResponse;
import com.example.hostelmanagement.utils.Momo.models.PaymentResponse;
import com.example.hostelmanagement.utils.Momo.models.QueryStatusTransactionResponse;
import com.example.hostelmanagement.utils.Momo.processor.ConfirmTransaction;
import com.example.hostelmanagement.utils.Momo.processor.CreateOrderMoMo;
import com.example.hostelmanagement.utils.Momo.processor.QueryTransactionStatus;
import com.example.hostelmanagement.utils.Momo.shared.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.lang.annotation.Target;
import java.sql.Timestamp;


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

    @RequestMapping(value = "{invoiceId}")
    public String createPayment(ModelMap mm,
                               HttpSession session,
                               @PathVariable int invoiceId
    ) throws Exception {
        try {
            LogUtils.init();

            String requestId = String.valueOf(System.currentTimeMillis());

            String orderId = String.valueOf(System.currentTimeMillis()) + "_InvoiceID" + invoiceId;

            Invoice invoice = invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid invoice Id:" + invoiceId));

            if (invoice.isPaymentStatus()) {
                mm.put("message", "Hoá đơn này đã được thanh toán thành công!!!");
                return "test2";
            }
            long amount = (long) invoice.getTotalAmount();
            String orderInfo = invoice.getInvoiceName();

            String returnURL = "http://localhost:8080/api/v1/Payment/queryPayment";
            String notifyURL = "http://localhost:8080/api/v1/Payment/queryPayment";

            //GET this from HOSTEL OWNER
            //Exampe owner.getSecretKey
            String partnerCode = "MOMOMWNB20210129";
            String accessKey = "nkDyGIefYvOL9Nyg";
            String secretKey = "YaCm3DJAJuAV9jGmwauQ0mwT6FpqYiOI";
            String endPoint = "https://test-payment.momo.vn/v2/gateway/api";

            PartnerInfo partnerInfo = new PartnerInfo(partnerCode, accessKey, secretKey);

            Environment environment = Environment.selectEnv("dev");
            environment.setPartnerInfo(partnerInfo);
            //Payment Method- Phương thức thanh toán
            PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET);
            //PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM);
            //PaymentResponse captureCreditMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_CREDIT);

            String redirectLink = captureWalletMoMoResponse.getPayUrl().toString();
            return "redirect:" + redirectLink;

        } catch (Exception ex) {
            mm.put("messages", "Error");
        }
        mm.put("messages", "Error");
        return "test2";
    }


    @RequestMapping(value = "/queryPayment")
    public String queryPayment(ModelMap mm,
                               HttpSession session,
                               @RequestParam String orderId,
                               @RequestParam String requestId
    ) throws Exception {
        try {
            LogUtils.init();

            //GET this from HOSTEL OWNER
            //Exampe owner.getSecretKey
            String partnerCode = "MOMOMWNB20210129";
            String accessKey = "nkDyGIefYvOL9Nyg";
            String secretKey = "YaCm3DJAJuAV9jGmwauQ0mwT6FpqYiOI";
            String endPoint = "https://test-payment.momo.vn/v2/gateway/api";

            PartnerInfo partnerInfo = new PartnerInfo(partnerCode, accessKey, secretKey);
            Environment environment = Environment.selectEnv("dev");
            environment.setPartnerInfo(partnerInfo);
            QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, orderId, requestId);

            String check = queryStatusTransactionResponse.getMessage();
            if (check.equalsIgnoreCase("Successful.")) {
                String toSplit = orderId;
                String result[] = toSplit.split("ID");
                String returnValue = result[result.length - 1];
                int invoiceID = Integer.parseInt(returnValue);
                Invoice invoice = invoiceRepository.findById(invoiceID)
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã hoá đơn:" + invoiceID));
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                invoice.setPaymentDate(timestamp);
                invoice.setPaymentStatus(true);
                invoiceRepository.save(invoice);
                mm.put("message", "Thanh toán thành công hoá đơn!");
            }

        } catch (Exception ex) {
            mm.put("message", "Có lỗi đã xảy ra!");
        }
        return "test2";
    }


    @RequestMapping(value = "/contract/{contractId}")
    public String createPaymentDeposit(ModelMap mm,
                                       HttpSession session,
                                       @PathVariable int contractId
    ) throws Exception {
        try {
            LogUtils.init();

            String requestId = String.valueOf(System.currentTimeMillis());

            String orderId = String.valueOf(System.currentTimeMillis()) + "_ContractDepositID" + contractId;

            Contracts contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy hợp đồng: " + contractId));

            if (contract.getDepositPaymentStatus()) {
                mm.put("message", "Tiền cọc này đã được thanh toán thành công!!!");
                return "test2";
            }

            long amount = (long) contract.getDeposit();
            String orderInfo = "TienCocHopDongID" + contract.getContractId();

            String returnURL = "http://localhost:8080/api/v1/Payment/contract/queryPayment";
            String notifyURL = "http://localhost:8080/api/v1/Payment/contract/queryPayment";

            //GET this from HOSTEL OWNER
            //Exampe owner.getSecretKey
            String partnerCode = "MOMOMWNB20210129";
            String accessKey = "nkDyGIefYvOL9Nyg";
            String secretKey = "YaCm3DJAJuAV9jGmwauQ0mwT6FpqYiOI";
            String endPoint = "https://test-payment.momo.vn/v2/gateway/api";

            PartnerInfo partnerInfo = new PartnerInfo(partnerCode, accessKey, secretKey);

            Environment environment = Environment.selectEnv("dev");
            environment.setPartnerInfo(partnerInfo);
            //Payment Method- Phương thức thanh toán
            PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET);
            //PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM);
            //PaymentResponse captureCreditMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_CREDIT);

            String redirectLink = captureWalletMoMoResponse.getPayUrl().toString();
            return "redirect:" + redirectLink;

        } catch (Exception ex) {
            mm.put("messages", "Error");
        }
        mm.put("messages", "Error");
        return "test2";
    }

    @RequestMapping(value = "/contract/queryPayment")
    public String queryPayment1(ModelMap mm,
                                HttpSession session,
                                @RequestParam String orderId,
                                @RequestParam String requestId
    ) throws Exception {
        try {
            LogUtils.init();

            //GET this from HOSTEL OWNER
            //Exampe owner.getSecretKey
            String partnerCode = "MOMOMWNB20210129";
            String accessKey = "nkDyGIefYvOL9Nyg";
            String secretKey = "YaCm3DJAJuAV9jGmwauQ0mwT6FpqYiOI";
            String endPoint = "https://test-payment.momo.vn/v2/gateway/api";

            PartnerInfo partnerInfo = new PartnerInfo(partnerCode, accessKey, secretKey);
            Environment environment = Environment.selectEnv("dev");
            environment.setPartnerInfo(partnerInfo);
            QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, orderId, requestId);

            String check = queryStatusTransactionResponse.getMessage();
            if (check.equalsIgnoreCase("Successful.")) {
                String toSplit = orderId;

                String result[] = toSplit.split("ID");
                String returnValue = result[result.length - 1];

                int contractId = Integer.parseInt(returnValue);
                Contracts contract = contractRepository.findById(contractId)
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã hợp đồng:" + contractId));
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                contract.setDepositPaymentStatus(true);

                contractRepository.save(contract);

                mm.put("message", "Thanh toán thành công tiền cọc!");
            }

        } catch (Exception ex) {
            mm.put("message", "Có lỗi đã xảy ra!");
        }
        return "test2";
    }





}


