package com.example.hostelmanagement.utils;

import javax.servlet.http.Part;
import java.io.IOException;

public class Utils {
    public static byte[] getByteImage(Part image) throws IOException {
        return image.getInputStream().readAllBytes();
    }
}
