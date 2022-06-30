package com.example.hostelmanagement.utils.Momo;

import com.example.hostelmanagement.utils.Momo.config.Environment;
import com.example.hostelmanagement.utils.Momo.enums.RequestType;
import com.example.hostelmanagement.utils.Momo.models.PaymentResponse;
import com.example.hostelmanagement.utils.Momo.processor.CreateOrderMoMo;
import com.example.hostelmanagement.utils.Momo.shared.utils.LogUtils;

public class TestMomo {

   public static void main(String... args) throws Exception {
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        long amount = 50000;

        String orderInfo = "Pay With MoMo";
        String returnURL = "https://google.com.vn";
        String notifyURL = "https://google.com.vn";

        Environment environment = Environment.selectEnv("dev");


//      Remember to change the IDs at enviroment.properties file

        //        Payment Method- Phương thức thanh toán
        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET);

        orderId = String.valueOf(System.currentTimeMillis());
        requestId = String.valueOf(System.currentTimeMillis());
        //        Payment Method- Phương thức thanh toán
        PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM);

        orderId = String.valueOf(System.currentTimeMillis());
        requestId = String.valueOf(System.currentTimeMillis());
        //        Payment Method- Phương thức thanh toán
        PaymentResponse captureCreditMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_CREDIT);

        ////        Transaction Query - Kiểm tra trạng thái giao dịch
     //   QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, orderId, requestId);

        ////        Transaction Refund - hoàn tiền giao dịch
        Long transId = 2L;
      //  RefundMoMoResponse refundMoMoResponse = RefundTransaction.process(environment, orderId, requestId, Long.toString(amount), transId, "");
    }
}
