package com.example.hostelmanagement.controllers;

import com.example.hostelmanagement.entities.QRCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Controller
public class QRCodeController {
    private final Logger logger = LogManager.getLogger(QRCodeController.class);
    public byte[] getQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            System.out.println("Error of getQRCodeImage");
        }
        return null;
    }
    @RequestMapping(value = "/qrcode")
    public void qrCode(String hostelId, HttpServletResponse response) {
        try {
            response.setContentType("image/png");
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(getQRCodeImage("http://localhost:8080/login?hostelId="+hostelId, 400, 400));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error of QRCodeController at: "+e.toString());
        }
    }
    @RequestMapping(value = "/loginqrcode", method = RequestMethod.POST)
    public String qrcodesite(@ModelAttribute(value = "qrcode") QRCode qrCode, ModelMap mm) {
        mm.put("hostelId", qrCode.getHostelId());
        logger.info(qrCode.getHostelId());
        return "qrcodesite";
    }
    @RequestMapping(value = "/loginqrcode", method = RequestMethod.GET)
    public String qrcodesite() {
        return "qrcodesite";
    }
}
